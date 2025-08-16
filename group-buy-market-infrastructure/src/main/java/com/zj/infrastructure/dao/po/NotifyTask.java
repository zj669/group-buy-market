package com.zj.infrastructure.dao.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotifyTask {
    private String id;
    private String activityId;
    private String teamId;
    private String notifyUrl;
    private String notifyCount;
    private String notifyStatus;
    private String parameterJson;
    private String createTime;
    private String updateTime;
}
