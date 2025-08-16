package com.zj.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 拼团订单枚举
 * @create 2025-01-26 16:21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum GroupBuyOrderEnumVO {

    PROGRESS(0, "拼单中"),
    COMPLETE(1, "完成"),
    FAIL(2, "失败"),
    ;

    private Integer code;
    private String info;

    public static GroupBuyOrderEnumVO valueOf(Integer code) {
        switch (code) {
            case 0:
                return PROGRESS;
            case 1:
                return COMPLETE;
            case 2:
                return FAIL;
        }
        throw new RuntimeException("err code not exist!");
    }

}
