package com.zj.domain.activity.service.trial.node;

import com.alibaba.fastjson2.JSON;
import com.zj.domain.activity.model.entity.MarketProductEntity;
import com.zj.domain.activity.model.entity.TrialBalanceEntity;
import com.zj.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.zj.domain.activity.model.valobj.GroupBuyActivityDiscountVO.GroupBuyDiscount;
import com.zj.domain.activity.model.valobj.SkuVO;
import com.zj.domain.activity.service.distinct.AbstractDistinctStrategy;
import com.zj.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.types.design.tree.handler.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service
public class MarketNode extends AbstractGroupBuyMarketSupport {
    @Resource
    private ErrorNode errorNode;
    @Resource
    private EndNode endNode;
    @Resource
    private Map<String, AbstractDistinctStrategy> distinctStrategyMap;

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParams, DynamicContext context) {
        log.info("拼团商品查询试算服务-MarketNode userId:{} requestParameter:{}", requestParams.getUserId(), JSON.toJSONString(requestParams));
        SkuVO skuVO = context.getSkuVO();
        log.info("原商品的信息" + JSON.toJSONString(context.getSkuVO()));
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO= context.getGroupBuyActivityDiscountVO();
        if (null == groupBuyActivityDiscountVO || null == skuVO) {
            return route(requestParams, context);
        }
        GroupBuyDiscount groupBuyDiscount = context.getGroupBuyActivityDiscountVO().getGroupBuyDiscount();
        Assert.notNull(groupBuyDiscount, "没有找到对应的优惠");
        AbstractDistinctStrategy distinctStrategy = distinctStrategyMap.get(groupBuyDiscount.getMarketPlan());
        distinctStrategy.distinct(context);
        return route(requestParams, context);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> getStrategyHandler(MarketProductEntity requestParams, DynamicContext context) {
        if(context.getSkuVO() == null || context.getGroupBuyActivityDiscountVO() == null){
            log.info("商品信息不存在，请检查输入");
            return errorNode;
        }
        return endNode;
    }

}
