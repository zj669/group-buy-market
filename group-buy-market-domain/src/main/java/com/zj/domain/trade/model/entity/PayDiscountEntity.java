package com.zj.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 拼团，支付优惠实体对象
 * @create 2025-01-05 16:46
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayDiscountEntity {

    /** 渠道 */
    private String source;
    /** 来源 */
    private String channel;
    /** 商品ID */
    private String goodsId;
    /** 商品名称 */
    private String goodsName;
    /** 原始价格 */
    private BigDecimal originalPrice;
    /** 折扣金额 */
    private BigDecimal deductionPrice;
    /** 外部交易单号-确保外部调用唯一幂等 */
    private String outTradeNo;

}
