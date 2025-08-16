package com.zj.domain.trade.service.settlement.filter.factory;

import com.zj.domain.trade.model.entity.MarketPayOrderEntity;
import com.zj.domain.trade.model.entity.TradeSettlementEntity;
import com.zj.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainFactory;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TradeSettlmentRuleFilterFactory extends AbstracSimpleChainFactory<TradeSettlementEntity, TradeSettlmentRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity > {
    @Resource
    private List<AbstracSimpleChainModel<TradeSettlementEntity, TradeSettlmentRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity >> modelList;

    @Override
    public void setChainModelList() {
        this.chainModelList = modelList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DynamicContext {
        private MarketPayOrderEntity marketPayOrderEntity;
    }
}
