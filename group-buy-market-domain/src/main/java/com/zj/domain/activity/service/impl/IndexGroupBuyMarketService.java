package com.zj.domain.activity.service.impl;

import com.zj.domain.activity.model.entity.MarketProductEntity;
import com.zj.domain.activity.model.entity.TrialBalanceEntity;
import com.zj.domain.activity.service.IIndexGroupBuyMarketService;
import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory;
import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.types.common.model.tree.handler.StrategyHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IndexGroupBuyMarketService implements IIndexGroupBuyMarketService {
    @Resource
    private DefaultActivityStragyFactory defaultActivityStragyFactory;
    @Override
    public TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception {
        StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> strategyHandler = defaultActivityStragyFactory.strategyHandler();
        return strategyHandler.apply(marketProductEntity, new DynamicContext());
    }
}
