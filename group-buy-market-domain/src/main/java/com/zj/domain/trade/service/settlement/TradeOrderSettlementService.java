package com.zj.domain.trade.service.settlement;

import com.zj.domain.trade.adapter.repository.ITradeRepository;
import com.zj.domain.trade.model.aggregate.LockOrderUpdateStatusAggregate;
import com.zj.domain.trade.model.entity.MarketPayOrderEntity;
import com.zj.domain.trade.model.entity.TradeSettlementEntity;
import com.zj.domain.trade.service.ITradeOrderSettlementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class TradeOrderSettlementService implements ITradeOrderSettlementService {
    @Resource
    private ITradeRepository tradeRepository;
    @Override
    public void settlement(TradeSettlementEntity tradeSettlementEntity) {
        String userId = tradeSettlementEntity.getUserId();
        String orderId = tradeSettlementEntity.getOrderId();
        MarketPayOrderEntity marketPayOrderEntity = tradeRepository.queryNoPayMarketPayOouOrder(userId, orderId);
        if (null == marketPayOrderEntity) {
            log.error("交易锁单记录(不存在): 用户id {}   订单id {}", userId, orderId);
            return;
        }
        tradeRepository.updateLockOrderStatus(LockOrderUpdateStatusAggregate.builder()
                .tradeSettlementEntity(tradeSettlementEntity)
                .marketPayOrderEntity(marketPayOrderEntity).build());
    }
}
