package com.zj.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SCSkuActivity {
    /**
     * 自增id
     */
    private Long id;
    /**
     * 来源
     */
    private String source;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 活动id
     */
    private Long activityId;
    /**
     * 商品id
     */
    private Long goodsId;
    private Long createTime;
    private Long updateTime;
}
