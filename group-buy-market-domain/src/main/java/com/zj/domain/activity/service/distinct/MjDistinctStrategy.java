package com.zj.domain.activity.service.distinct;

import com.zj.domain.activity.model.valobj.SkuVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component("MJ")
public class MjDistinctStrategy extends AbstractDistinctStrategy {

    @Override
    public BigDecimal calculate(SkuVO skuVO, String[] split) {
        if(skuVO.getOriginalPrice().compareTo( new BigDecimal(split[0]))<0){
            return skuVO.getOriginalPrice();
        }
        log.info("开始进行满减优惠");
        BigDecimal originalPrice = skuVO.getOriginalPrice();
        BigDecimal curPrice = originalPrice.subtract(new BigDecimal(split[1]));
        if(curPrice.compareTo(BigDecimal.ZERO) < 0){
            curPrice  = new BigDecimal("0.01");
        }
        return curPrice;
    }
}
