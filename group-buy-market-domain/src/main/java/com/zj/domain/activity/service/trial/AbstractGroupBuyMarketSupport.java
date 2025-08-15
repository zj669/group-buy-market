package com.zj.domain.activity.service.trial;

import com.zj.domain.activity.model.entity.MarketProductEntity;
import com.zj.domain.activity.model.entity.TrialBalanceEntity;
import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.types.design.tree.AbsractMultiTreadStrategyRouter;

public abstract class AbstractGroupBuyMarketSupport extends AbsractMultiTreadStrategyRouter<MarketProductEntity, DynamicContext, TrialBalanceEntity> {
    @Override
    protected void multiThread(MarketProductEntity requestParams, DynamicContext context) {

    }
}
