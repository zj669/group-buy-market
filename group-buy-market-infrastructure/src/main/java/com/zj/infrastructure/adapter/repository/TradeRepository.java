package com.zj.infrastructure.adapter.repository;

import com.alibaba.fastjson2.JSON;
import com.zj.domain.trade.adapter.repository.ITradeRepository;
import com.zj.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import com.zj.domain.trade.model.aggregate.LockOrderUpdateStatusAggregate;
import com.zj.domain.trade.model.entity.*;
import com.zj.domain.trade.model.valobj.GroupBuyProgressVO;
import com.zj.domain.trade.model.valobj.TradeOrderStatusEnumVO;
import com.zj.infrastructure.dao.IGroupBuyActivityDao;
import com.zj.infrastructure.dao.IGroupBuyOrderDao;
import com.zj.infrastructure.dao.IGroupBuyOrderListDao;
import com.zj.infrastructure.dao.INotifyTaskDao;
import com.zj.infrastructure.dao.po.GroupBuyActivity;
import com.zj.infrastructure.dao.po.GroupBuyOrder;
import com.zj.infrastructure.dao.po.GroupBuyOrderList;
import com.zj.infrastructure.dao.po.NotifyTask;
import com.zj.types.enums.ActivityStatusEnumVO;
import com.zj.types.enums.ResponseCode;
import com.zj.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Slf4j
@Repository
public class TradeRepository implements ITradeRepository {
    @Resource
    private IGroupBuyOrderDao groupBuyOrderDao;
    @Resource
    private IGroupBuyOrderListDao groupBuyOrderListDao;
    @Resource
    private IGroupBuyActivityDao groupBuyActivityDao;
    @Resource
    private INotifyTaskDao notifyTaskDao;

    /**
     * 根据外部订单查询
     * @param userId 用户id
     * @param outTradeNo 订单号
     * @return
     */
    @Override
    public MarketPayOrderEntity queryNoPayMarketPayOouOrder(String userId, String outTradeNo) {
        GroupBuyOrderList groupBuyOrderList = groupBuyOrderListDao.queryGroupBuyOrderRecordByOutTradeNo(GroupBuyOrderList.builder()
                .userId(userId)
                .outTradeNo(outTradeNo)
                .build());
        if(groupBuyOrderList == null){
            log.info("未查询到订单");
            return null;
        }
        return MarketPayOrderEntity.builder()
                .teamId(groupBuyOrderList.getTeamId())
                .orderId(groupBuyOrderList.getOrderId())
                .deductionPrice(groupBuyOrderList.getDeductionPrice())
                .tradeOrderStatusEnumVO(TradeOrderStatusEnumVO.valueOf(groupBuyOrderList.getStatus()))
                .build();
    }

    /**
     * 查询拼团进度
     * @param teamId 团号
     * @return
     */
    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        GroupBuyOrder groupBuyOrder = groupBuyOrderDao.queryGroupBuyProgress(teamId);

        return GroupBuyProgressVO.builder()
                .completeCount(groupBuyOrder.getCompleteCount())
                .targetCount(groupBuyOrder.getTargetCount())
                .lockCount(groupBuyOrder.getLockCount())
                .build();
    }

    /**
     * 用户参加拼团
     * @param groupBuyOrderAggregate
     * @return
     */
    @Override
    public MarketPayOrderEntity lockPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate) {
        UserEntity userEntity = groupBuyOrderAggregate.getUserEntity();
        PayActivityEntity payActivityEntity = groupBuyOrderAggregate.getPayActivityEntity();
        PayDiscountEntity payDiscountEntity = groupBuyOrderAggregate.getPayDiscountEntity();
        // 1 看用户是否第一次拼团
        String teamId = payActivityEntity.getTeamId();
        if(StringUtils.isBlank(teamId)){
            // 是则新建，则生成唯一的拼团id
            teamId = RandomStringUtils.randomNumeric(8);
            GroupBuyOrder groupBuyOrder = GroupBuyOrder.builder()
                    .teamId(teamId)
                    .activityId(payActivityEntity.getActivityId())
                    .lockCount(1)
                    .channel(payDiscountEntity.getChannel())
                    .source(payDiscountEntity.getSource())
                    .completeCount(0)
                    .targetCount(payActivityEntity.getTargetCount())
                    .deductionPrice(payDiscountEntity.getDeductionPrice())
                    .originalPrice(payDiscountEntity.getOriginalPrice())
                    .payPrice(payDiscountEntity.getDeductionPrice())
                    .build();
            groupBuyOrderDao.insert(groupBuyOrder);
        }else{
            // 不是则看能否成团
            int i = groupBuyOrderDao.updateAddLockCount(teamId);
            if( i!= 1){
                throw new RuntimeException("拼团失败");
            }
        }
        // 保存详细信息
        String orderId = RandomStringUtils.randomNumeric(12);
        String bizId = RandomStringUtils.randomNumeric(12);
        GroupBuyOrderList groupBuyOrderListReq = GroupBuyOrderList.builder()
                .userId(userEntity.getUserId())
                .teamId(teamId)
                .orderId(orderId)
                .activityId(payActivityEntity.getActivityId())
                .startTime(payActivityEntity.getStartTime())
                .endTime(payActivityEntity.getEndTime())
                .goodsId(payDiscountEntity.getGoodsId())
                .source(payDiscountEntity.getSource())
                .channel(payDiscountEntity.getChannel())
                .originalPrice(payDiscountEntity.getDeductionPrice())
                .deductionPrice(payDiscountEntity.getDeductionPrice())
                .status(TradeOrderStatusEnumVO.CREATE.getCode())
                .outTradeNo(payDiscountEntity.getOutTradeNo())
                .bizId(bizId)
                .build();

        groupBuyOrderListDao.insert(groupBuyOrderListReq);
        return MarketPayOrderEntity.builder()
                .orderId(orderId)
                .deductionPrice(payDiscountEntity.getDeductionPrice())
                .tradeOrderStatusEnumVO(TradeOrderStatusEnumVO.CREATE)
                .build();
    }

    @Override
    public GroupBuyActivityEntity queryGroupBuyActivityEntityByActivityId(Long activityId) {
        GroupBuyActivity groupBuyActivity = groupBuyActivityDao.queryValidGroupBuyActivity(GroupBuyActivity.builder().activityId(activityId).build());
        return GroupBuyActivityEntity.builder()
                .activityName(groupBuyActivity.getActivityName())
                .activityId(groupBuyActivity.getActivityId())
                .discountId(groupBuyActivity.getDiscountId())
                .groupType(groupBuyActivity.getGroupType())
                .takeLimitCount(groupBuyActivity.getTakeLimitCount())
                .target(groupBuyActivity.getTarget())
                .validTime(groupBuyActivity.getValidTime())
                .status(ActivityStatusEnumVO.valueOf(groupBuyActivity.getStatus()))
                .startTime(groupBuyActivity.getStartTime())
                .endTime(groupBuyActivity.getEndTime())
                .tagId(groupBuyActivity.getTagId())
                .tagScope(groupBuyActivity.getTagScope())
                .build();
    }

    @Override
    public Integer queryOrderCountByActivityId(Long activityId, String userId) {
        return groupBuyOrderListDao.queryOrderCountByActivityId(GroupBuyOrderList.builder().activityId(activityId).userId(userId).build());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateLockOrderStatus(LockOrderUpdateStatusAggregate lockOrderUpdateStatusAggregate) {
        //1. order orderList

        GroupBuyOrder groupBuyOrder = groupBuyOrderDao.queryGroupBuyProgress(lockOrderUpdateStatusAggregate.getMarketPayOrderEntity().getTeamId());

        Integer completeCount = groupBuyOrder.getCompleteCount();
        Integer lockCount = groupBuyOrder.getLockCount();
        Integer targetCount = groupBuyOrder.getTargetCount();
        if(completeCount + lockCount > targetCount){
            throw new AppException(ResponseCode.E0010);
        }

        groupBuyOrderDao.updateLockOrderComplete(GroupBuyOrder.builder()
                .teamId(lockOrderUpdateStatusAggregate.getMarketPayOrderEntity().getTeamId())
                .completeCount(completeCount + 1)
                .lockCount(lockCount - 1)
                .build());

        groupBuyOrderListDao.updateLockOrderComplete(GroupBuyOrderList.builder()
                .orderId(lockOrderUpdateStatusAggregate.getTradeSettlementEntity().getOrderId())
                .userId(lockOrderUpdateStatusAggregate.getTradeSettlementEntity().getUserId())
                .status(TradeOrderStatusEnumVO.COMPLETE.getCode())
                .build());

        if(completeCount + 1 == targetCount){
            // todo 插入一条回调任务数据
            notifyTaskDao.insert(NotifyTask.builder()
                            .activityId(String.valueOf(groupBuyOrder.getActivityId()))
                    .notifyUrl("没有")
                    .notifyCount("0")
                    .notifyStatus("0")
                    .parameterJson(JSON.toJSONString(lockOrderUpdateStatusAggregate))
                    .createTime("")
                            .updateTime( "")
                    .teamId(groupBuyOrder.getTeamId())
                    .build());
        }
    }


}
