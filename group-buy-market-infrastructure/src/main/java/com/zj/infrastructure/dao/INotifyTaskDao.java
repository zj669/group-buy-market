package com.zj.infrastructure.dao;

import com.zj.infrastructure.dao.po.NotifyTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface INotifyTaskDao {
    void insert(NotifyTask build);
}
