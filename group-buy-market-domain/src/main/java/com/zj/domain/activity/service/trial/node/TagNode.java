package com.zj.domain.activity.service.trial.node;

import com.zj.domain.activity.adapter.repository.IActivityRepository;
import com.zj.domain.activity.model.entity.MarketProductEntity;
import com.zj.domain.activity.model.entity.TrialBalanceEntity;
import com.zj.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.zj.domain.activity.model.valobj.SkuVO;
import com.zj.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.domain.activity.service.trial.threadTask.QueryGroupBuyActivityDataTask;
import com.zj.domain.activity.service.trial.threadTask.QuerySkuDataTask;
import com.zj.types.design.tree.handler.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class TagNode extends AbstractGroupBuyMarketSupport {
    @Resource
    private IActivityRepository activityRepository;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private MarketNode marketNode;
    @Resource
    private ErrorNode errorNode;
    @Resource
    private EndNode endNode;
    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParams, DynamicContext context) {
        log.info("开始进行人群筛选");
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO= context.getGroupBuyActivityDiscountVO();
        if (null == groupBuyActivityDiscountVO) {
            return route(requestParams, context);
        }
        String tagId = groupBuyActivityDiscountVO.getTagId();
        boolean visible = groupBuyActivityDiscountVO.getIsVisible();
        boolean enable = groupBuyActivityDiscountVO.getIsEnable();

        // 人群标签配置为空，则走默认值
        if (StringUtils.isBlank(tagId)) {
            context.setIsVisible(true);
            context.setIsEnable(true);
            return route(requestParams, context);
        }

        // 是否在人群范围内；visible、enable 如果值为 ture 则表示没有配置拼团限制，那么就直接保证为 true 即可 todo 理解这个状态
        boolean isWithin = activityRepository.isTagCrowdRange(tagId, requestParams.getUserId());
        log.info("用户是否在人群内：{}", isWithin);
        context.setIsVisible(visible || isWithin);
        context.setIsEnable(enable || isWithin);
        return route(requestParams, context);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> getStrategyHandler(MarketProductEntity requestParams, DynamicContext context) {
        if(context.getSkuVO() == null || context.getGroupBuyActivityDiscountVO() == null){
            log.info("商品信息不存在，请检查输入");
            return errorNode;
        }
        if(context.getIsEnable()){
            return marketNode;
        }
        return endNode;
    }

    @Override
    protected void multiThread(MarketProductEntity requestParams, DynamicContext context) {
        QuerySkuDataTask querySkuDataTask = new QuerySkuDataTask(requestParams.getGoodsId(), activityRepository);
        FutureTask<SkuVO> skuDataTask = new FutureTask<>(querySkuDataTask);
        threadPoolExecutor.execute(skuDataTask);

        QueryGroupBuyActivityDataTask queryGroupBuyActivityDataTask = new QueryGroupBuyActivityDataTask(requestParams.getGoodsId(), activityRepository);
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
