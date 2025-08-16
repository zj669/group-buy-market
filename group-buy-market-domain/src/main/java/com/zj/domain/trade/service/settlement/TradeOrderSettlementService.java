package com.zj.domain.trade.service.settlement;

import com.zj.domain.trade.adapter.repository.ITradeRepository;
import com.zj.domain.trade.model.aggregate.LockOrderUpdateStatusAggregate;
import com.zj.domain.trade.model.entity.MarketPayOrderEntity;
import com.zj.domain.trade.model.entity.TradeSettlementEntity;
import com.zj.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import com.zj.domain.trade.service.ITradeOrderSettlementService;
import com.zj.domain.trade.service.settlement.filter.factory.TradeSettlmentRuleFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class TradeOrderSettlementService implements ITradeOrderSettlementService {
    @Resource
    private ITradeRepository tradeRepository;
    @Resource
    private TradeSettlmentRuleFilterFactory tradeSettlmentRuleFilterFactory;
    @Override
    public void settlement(TradeSettlementEntity tradeSettlementEntity) {
        log.info("拼团交易-支付订单结算:{} outTradeNo:{}", tradeSettlementEntity.getUserId(), tradeSettlementEntity.getOutTradeNo());
        TradeSettlementRuleFilterBackEntity tradeSettlementRuleFilterBackEntity = tradeSettlmentRuleFilterFactory.getChain().handle(
                TradeSettlementEntity.builder()
                        .source(tradeSettlementEntity.getSource())
                        .channel(tradeSettlementEntity.getChannel())
                        .userId(tradeSettlementEntity.getUserId())
                        .outTradeNo(tradeSettlementEntity.getOutTradeNo())
                        .outTradeTime(tradeSettlementEntity.getOutTradeTime())
                        .build(),
                new TradeSettlmentRuleFilterFactory.DynamicContext());

        MarketPayOrderEntity marketPayOrderEntity = tradeSettlementRuleFilterBackEntity.getDynamicContext().getMarketPayOrderEntity(); ;
        tradeRepository.updateLockOrderStatus(LockOrderUpdateStatusAggregate.builder()
                .tradeSettlementEntity(tradeSettlementEntity)
                .marketPayOrderEntity(marketPayOrderEntity).build());
    }
}
