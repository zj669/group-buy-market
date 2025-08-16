package com.zj.domain.trade.service.settlement.filter.node;

import com.zj.domain.trade.model.entity.TradeSettlementEntity;
import com.zj.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import com.zj.domain.trade.service.settlement.filter.factory.TradeSettlmentRuleFilterFactory;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(4)
public class EndRuleFilter extends AbstracSimpleChainModel<TradeSettlementEntity, TradeSettlmentRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {
    @Override
    protected TradeSettlementRuleFilterBackEntity apply(TradeSettlementEntity tradeSettlementEntity, TradeSettlmentRuleFilterFactory.DynamicContext dynamicContext) {
        log.info("进入节点4");
        return null;
    }
}
