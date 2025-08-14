package com.zj.domain.trade.model.valobj;

import lombok.*;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 交易订单状态枚举
 * @create 2025-01-11 10:21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TradeOrderStatusEnumVO {

    CREATE(0, "初始创建"),
    COMPLETE(1, "消费完成"),
    CLOSE(2, "超时关单"),
    ;

    private Integer code;
    private String info;

    public static TradeOrderStatusEnumVO valueOf(Integer code) {
        return switch (code) {
            case 0 -> CREATE;
            case 1 -> COMPLETE;
            case 2 -> CLOSE;
            default -> CREATE;
        };
    }

}
