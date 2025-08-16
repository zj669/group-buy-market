package com.zj.domain.trade.service;

import com.zj.domain.trade.model.entity.TradeSettlementEntity;

public interface ITradeOrderSettlementService {

    void settlement(TradeSettlementEntity tradeSettlementEntity);
}
