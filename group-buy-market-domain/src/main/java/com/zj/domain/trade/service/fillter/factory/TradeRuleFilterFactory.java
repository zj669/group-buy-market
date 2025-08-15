package com.zj.domain.trade.service.fillter.factory;

import com.zj.domain.trade.model.entity.GroupBuyActivityEntity;
import com.zj.domain.trade.model.entity.TradeRuleCommandEntity;
import com.zj.domain.trade.model.entity.TradeRuleFilterBackEntity;
import com.zj.domain.trade.service.fillter.factory.TradeRuleFilterFactory.DynamicContext;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class TradeRuleFilterFactory extends AbstracSimpleChainFactory<TradeRuleCommandEntity, DynamicContext, TradeRuleFilterBackEntity>  {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        private GroupBuyActivityEntity groupBuyActivity;

    }
}
