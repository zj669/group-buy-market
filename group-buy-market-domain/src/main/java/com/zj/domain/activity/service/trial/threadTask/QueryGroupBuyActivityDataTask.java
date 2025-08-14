package com.zj.domain.activity.service.trial.threadTask;

import com.zj.domain.activity.adapter.repository.IActivityRepository;
import com.zj.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.util.concurrent.Callable;

public class QueryGroupBuyActivityDataTask implements Callable<GroupBuyActivityDiscountVO> {


    /**
     * 商品id
     */
    private final String goodsId;

    /**
     * 活动仓储
     */
    private final IActivityRepository activityRepository;

    public QueryGroupBuyActivityDataTask(String goodsId, IActivityRepository activityRepository) {
        this.goodsId = goodsId;
        this.activityRepository = activityRepository;
    }

    @Override
    public GroupBuyActivityDiscountVO call() throws Exception {
        return activityRepository.queryGroupBuyActivityDiscountVO(goodsId);
    }

}
