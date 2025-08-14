package com.zj.domain.activity.adapter.repository;

import com.zj.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.zj.domain.activity.model.valobj.SkuVO;
import org.springframework.stereotype.Repository;

@Repository
public interface IActivityRepository {

    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String goodsId);

    SkuVO querySkuByGoodsId(String goodsId);

}
