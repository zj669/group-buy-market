package com.zj.domain.activity.service.distinct;

import com.zj.domain.activity.model.valobj.GroupBuyActivityDiscountVO.GroupBuyDiscount;
import com.zj.domain.activity.model.valobj.SkuVO;
import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.types.common.Constants;
import com.zj.types.enums.ResponseCode;
import com.zj.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component("N")
public class NDistinctStrategy extends AbstractDistinctStrategy {

    @Override
    public BigDecimal calculate(SkuVO skuVO, String[] split) {
        log.info("开始进行N元购优惠");
        BigDecimal curPrice = new BigDecimal(split[0]);
        if(curPrice.compareTo(BigDecimal.ZERO) < 0){
            curPrice  = new BigDecimal("0.01");
        }
        return curPrice;
    }
}
