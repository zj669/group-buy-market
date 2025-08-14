package com.zj.infrastructure.dao;

import com.zj.infrastructure.dao.po.SCSkuActivity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISCSkuActivityDao {
    SCSkuActivity querySCSkuActivityBySCGoodsId(SCSkuActivity scSkuActivity);
}
