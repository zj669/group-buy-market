package com.zj.domain.trade.model.aggregate;

import com.zj.domain.trade.model.entity.MarketPayOrderEntity;
import com.zj.domain.trade.model.entity.TradeSettlementEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LockOrderUpdateStatusAggregate {
    private TradeSettlementEntity tradeSettlementEntity;
    private MarketPayOrderEntity marketPayOrderEntity;
}
