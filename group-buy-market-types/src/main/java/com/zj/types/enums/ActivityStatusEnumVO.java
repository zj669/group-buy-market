package com.zj.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 拼团活动状态枚举
 * @create 2025-01-25 12:29
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ActivityStatusEnumVO {

    CREATE(0, "创建"),
    EFFECTIVE(1, "生效"),
    OVERDUE(2, "过期"),
    ABANDONED(3, "废弃"),
    ;

    private Integer code;
    private String info;

    public static ActivityStatusEnumVO valueOf(Integer code) {
        switch (code) {
            case 0:
                return CREATE;
            case 1:
                return EFFECTIVE;
            case 2:
                return OVERDUE;
            case 3:
                return ABANDONED;
        }
        throw new RuntimeException("err code not exist!");
    }

}
