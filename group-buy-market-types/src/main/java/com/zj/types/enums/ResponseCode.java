package com.zj.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("0000", "成功"),
    UN_ERROR("0001", "未知失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),

    E0006("0006", "拼团人数已经满"),
    E0007("0007", "活动参与非法"),
    E0008("008","用户已达上限"),
    E0010("0010","拼团已经达到上线")
    ;

    private String code;
    private String info;

}
