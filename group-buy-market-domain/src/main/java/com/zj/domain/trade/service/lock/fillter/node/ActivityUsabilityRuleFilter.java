package com.zj.domain.trade.service.lock.fillter.node;

import com.zj.domain.trade.adapter.repository.ITradeRepository;
import com.zj.domain.trade.model.entity.GroupBuyActivityEntity;
import com.zj.domain.trade.model.entity.TradeRuleCommandEntity;
import com.zj.domain.trade.model.entity.TradeRuleFilterBackEntity;
import com.zj.domain.trade.service.lock.fillter.factory.TradeRuleFilterFactory;
import com.zj.domain.trade.service.lock.fillter.factory.TradeRuleFilterFactory.DynamicContext;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainModel;
import com.zj.types.enums.ActivityStatusEnumVO;
import com.zj.types.enums.ResponseCode;
import com.zj.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
@Order(1)
public class ActivityUsabilityRuleFilter extends AbstracSimpleChainModel<TradeRuleCommandEntity, DynamicContext, TradeRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeRuleFilterBackEntity apply(TradeRuleCommandEntity requestParameter, TradeRuleFilterFactory.DynamicContext dynamicContext){
        log.info("交易规则过滤1-活动的可用性校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());

        // 查询拼团活动
        GroupBuyActivityEntity groupBuyActivity = repository.queryGroupBuyActivityEntityByActivityId(requestParameter.getActivityId());

        // 校验；活动状态 - 可以抛业务异常code，或者把code写入到动态上下文dynamicContext中，最后获取。
        if (!ActivityStatusEnumVO.EFFECTIVE.equals(groupBuyActivity.getStatus())) {
            log.info("活动的可用性校验，非生效状态 activityId:{}", requestParameter.getActivityId());
            throw new AppException(ResponseCode.E0007.getInfo());
        }

        // 校验；活动时间
        Date currentTime = new Date();
        if (currentTime.before(groupBuyActivity.getStartTime()) || currentTime.after(groupBuyActivity.getEndTime())) {
            log.info("活动的可用性校验，非可参与时间范围 activityId:{}", requestParameter.getActivityId());
            throw new AppException(ResponseCode.E0007.getInfo());
        }

        // 写入动态上下文
        dynamicContext.setGroupBuyActivity(groupBuyActivity);

        // 走到下一个责任链节点
        return TradeRuleFilterBackEntity.builder().build();
    }

}