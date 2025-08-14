package com.zj.domain.activity.service.trial.node;

import com.zj.domain.activity.model.entity.MarketProductEntity;
import com.zj.domain.activity.model.entity.TrialBalanceEntity;
import com.zj.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.types.common.model.tree.handler.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SwitchNode extends AbstractGroupBuyMarketSupport {
    @Resource
    private TagNode tagNode;

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParams, DynamicContext context) {
        return route(requestParams, context);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> getStrategyHandler(MarketProductEntity requestParams, DynamicContext context) {
        return tagNode;
    }
}
