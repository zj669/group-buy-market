package com.zj.domain.activity.service.trial.node;

import com.alibaba.fastjson2.JSON;
import com.zj.domain.activity.model.entity.MarketProductEntity;
import com.zj.domain.activity.model.entity.TrialBalanceEntity;
import com.zj.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.zj.domain.activity.model.valobj.SkuVO;
import com.zj.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.types.design.tree.handler.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EndNode extends AbstractGroupBuyMarketSupport {
    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParams, DynamicContext context) {
        log.info("拼团商品查询试算服务-EndNode userId:{} requestParameter:{}", requestParams.getUserId(), JSON.toJSONString(requestParams));

        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = context.getGroupBuyActivityDiscountVO();
        SkuVO skuVO = context.getSkuVO();

        // 返回空结果
        return TrialBalanceEntity.builder()
                .goodsId(skuVO.getGoodsId())
                .goodsName(skuVO.getGoodsName())
                .originalPrice(skuVO.getOriginalPrice())
                .deductionPrice(context.getDeductionPrice() == null? skuVO.getOriginalPrice() :context.getDeductionPrice())
                .targetCount(groupBuyActivityDiscountVO.getTarget())
                .startTime(groupBuyActivityDiscountVO.getStartTime())
                .endTime(groupBuyActivityDiscountVO.getEndTime())
                .isVisible(context.getIsVisible())
                .isEnable(context.getIsEnable())
                .groupBuyActivityDiscountVO(groupBuyActivityDiscountVO)
                .build();
    }

    @Override
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> getStrategyHandler(MarketProductEntity requestParams, DynamicContext context) {
        return getDefaultStrategyHandler();
    }
}
