package com.zj.domain.trade.adapter.repository;

import com.zj.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import com.zj.domain.trade.model.aggregate.LockOrderUpdateStatusAggregate;
import com.zj.domain.trade.model.entity.GroupBuyActivityEntity;
import com.zj.domain.trade.model.entity.MarketPayOrderEntity;
import com.zj.domain.trade.model.entity.TradeSettlementEntity;
import com.zj.domain.trade.model.valobj.GroupBuyProgressVO;
import org.springframework.stereotype.Repository;

@Repository
public interface ITradeRepository {
    /**
     * 查询是否是锁单账户，且状态为初始的
     * @param userId 用户ID
     * @param outTradeNo 订单号
     * @return 锁单账户
     */
    MarketPayOrderEntity queryNoPayMarketPayOouOrder(String userId, String outTradeNo);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    MarketPayOrderEntity lockPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    GroupBuyActivityEntity queryGroupBuyActivityEntityByActivityId(Long activityId);

    Integer queryOrderCountByActivityId(Long activityId, String userId);

    void updateLockOrderStatus(LockOrderUpdateStatusAggregate lockOrderUpdateStatusAggregate);
}
