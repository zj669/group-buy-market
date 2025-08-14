package com.zj.domain.activity.service.trial.node;

import com.alibaba.fastjson2.JSON;
import com.zj.domain.activity.adapter.repository.IActivityRepository;
import com.zj.domain.activity.model.entity.MarketProductEntity;
import com.zj.domain.activity.model.entity.TrialBalanceEntity;
import com.zj.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.zj.domain.activity.model.valobj.GroupBuyActivityDiscountVO.GroupBuyDiscount;
import com.zj.domain.activity.model.valobj.SkuVO;
import com.zj.domain.activity.service.distinct.AbstractDistinctStrategy;
import com.zj.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.domain.activity.service.trial.threadTask.QueryGroupBuyActivityDataTask;
import com.zj.domain.activity.service.trial.threadTask.QuerySkuDataTask;
import com.zj.types.common.model.tree.handler.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class MarketNode extends AbstractGroupBuyMarketSupport {
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private EndNode endNode;
    @Resource
    private IActivityRepository activityRepository;
    @Resource
    private Map<String, AbstractDistinctStrategy> distinctStrategyMap;


    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParams, DynamicContext context) {
        log.info("拼团商品查询试算服务-MarketNode userId:{} requestParameter:{}", requestParams.getUserId(), JSON.toJSONString(requestParams));
        // todo 对商品进行优惠
        log.info("原商品的信息" + JSON.toJSONString(context.getSkuVO()));
        GroupBuyDiscount groupBuyDiscount = context.getGroupBuyActivityDiscountVO().getGroupBuyDiscount();
        Assert.notNull(groupBuyDiscount, "没有找到对应的优惠");
        AbstractDistinctStrategy distinctStrategy = distinctStrategyMap.get(groupBuyDiscount.getMarketPlan());
        distinctStrategy.distinct(context);
        return route(requestParams, context);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> getStrategyHandler(MarketProductEntity requestParams, DynamicContext context) {
        return endNode;
    }

    @Override
    protected void multiThread(MarketProductEntity requestParams, DynamicContext context) {
        QuerySkuDataTask querySkuDataTask = new QuerySkuDataTask(requestParams.getGoodsId(), activityRepository);
        FutureTask<SkuVO> skuDataTask = new FutureTask<>(querySkuDataTask);
        threadPoolExecutor.execute(skuDataTask);

        QueryGroupBuyActivityDataTask queryGroupBuyActivityDataTask = new QueryGroupBuyActivityDataTask(requestParams.getSource(), requestParams.getChannel(), activityRepository);
        FutureTask<GroupBuyActivityDiscountVO> groupBuyActivityDiscountVOFutureTask = new FutureTask<>(queryGroupBuyActivityDataTask);
        threadPoolExecutor.execute(groupBuyActivityDiscountVOFutureTask);
        Long timeout = 5L;
        try {
            context.setSkuVO(skuDataTask.get(timeout, TimeUnit.MINUTES));
            context.setGroupBuyActivityDiscountVO(groupBuyActivityDiscountVOFutureTask.get(timeout, TimeUnit.MINUTES));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.info("异步查询超时");
            throw new RuntimeException(e);
        }
        log.info("异步加载数据完成");
    }
}
