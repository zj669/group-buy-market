package com.zj.domain.activity.service.distinct;

import com.zj.domain.activity.model.valobj.GroupBuyActivityDiscountVO.GroupBuyDiscount;
import com.zj.domain.activity.model.valobj.SkuVO;
import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.types.common.Constants;
import com.zj.types.enums.ResponseCode;
import com.zj.types.exception.AppException;

import java.math.BigDecimal;

public abstract class AbstractDistinctStrategy {

    public void distinct(DynamicContext context){
        SkuVO skuVO = context.getSkuVO();
        GroupBuyDiscount discount = context.getGroupBuyActivityDiscountVO().getGroupBuyDiscount();
        String[] split = discount.getMarketExpr().split(Constants.SPLIT);
        if(split.length == 0){
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(),"优惠配置错误");
        }
        BigDecimal curPrice = calculate(skuVO, split);
        context.setDeductionPrice(curPrice);
    };

    public abstract BigDecimal calculate(SkuVO skuVO, String[] split);
}
