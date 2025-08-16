package com.zj.test.domain;

import com.zj.domain.trade.model.entity.TradeSettlementEntity;
import com.zj.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import com.zj.domain.trade.service.settlement.TradeOrderSettlementService;
import com.zj.domain.trade.service.settlement.filter.factory.TradeSettlmentRuleFilterFactory;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IMarketTradeLSettlmentServiceTest {
    @Resource
    private TradeOrderSettlementService marketTradeLSettlmentService;
    @Resource
    private TradeSettlmentRuleFilterFactory tradeSettlmentRuleFilterFactory;

    @Test
    public void test_settlement() {
        marketTradeLSettlmentService.settlement(TradeSettlementEntity.builder()
                        .outTradeNo("411481433880")
                        .userId("xiaofuge")
                .build());
    }

    @Test
    public void test_settlement_Complete() {
        marketTradeLSettlmentService.settlement(TradeSettlementEntity.builder()
                        .userId("liergou")
                .outTradeNo("843867108777")
                .build());
    }

    @Test
    public void test_simpleChain(){
        AbstracSimpleChainModel<TradeSettlementEntity, TradeSettlmentRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> chain = tradeSettlmentRuleFilterFactory.getChain();
        chain.handle(TradeSettlementEntity.builder().build(),
                new TradeSettlmentRuleFilterFactory.DynamicContext()
                );
    }

}
