package com.zj.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeSettlementEntity {
    /**
     * 外部订单ID
     */
    private String orderOutId;
    /**
     * 用户ID
     */
    private String userId;
}
