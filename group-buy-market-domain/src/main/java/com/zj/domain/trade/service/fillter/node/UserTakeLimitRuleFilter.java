package com.zj.domain.trade.service.fillter.node;


import com.zj.domain.trade.adapter.repository.ITradeRepository;
import com.zj.domain.trade.model.entity.GroupBuyActivityEntity;
import com.zj.domain.trade.model.entity.TradeRuleCommandEntity;
import com.zj.domain.trade.model.entity.TradeRuleFilterBackEntity;
import com.zj.domain.trade.service.fillter.factory.TradeRuleFilterFactory;
import com.zj.domain.trade.service.fillter.factory.TradeRuleFilterFactory.DynamicContext;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainModel;
import com.zj.types.enums.ResponseCode;
import com.zj.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 用户参与限制，规则过滤
 * @create 2025-01-25 09:19
 */
@Slf4j
@Service
@Order(2)
public class UserTakeLimitRuleFilter extends AbstracSimpleChainModel<TradeRuleCommandEntity, DynamicContext, TradeRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeRuleFilterBackEntity apply(TradeRuleCommandEntity requestParameter, TradeRuleFilterFactory.DynamicContext dynamicContext){
        log.info("交易规则过滤-用户参与次数校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());

        GroupBuyActivityEntity groupBuyActivity = dynamicContext.getGroupBuyActivity();

        // 查询用户在一个拼团活动上参与的次数
        Integer count = repository.queryOrderCountByActivityId(requestParameter.getActivityId(), requestParameter.getUserId());

        if (null != groupBuyActivity.getTakeLimitCount() && count >= groupBuyActivity.getTakeLimitCount()) {
            log.info("用户参与次数校验，已达可参与上限 activityId:{}, 次数 {}", requestParameter.getActivityId(), count);

            throw new AppException(ResponseCode.E0008.getInfo());
        }

        return TradeRuleFilterBackEntity.builder()
                .userTakeOrderCount(count)
                .build();
    }

}
