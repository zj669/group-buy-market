package com.zj.domain.activity.service.trial.threadTask;

import com.zj.domain.activity.adapter.repository.IActivityRepository;
import com.zj.domain.activity.model.valobj.SkuVO;

import java.util.concurrent.Callable;

public class QuerySkuDataTask implements Callable<SkuVO> {

    private final String goodsId;

    private final IActivityRepository activityRepository;

    public QuerySkuDataTask(String goodsId, IActivityRepository activityRepository) {
        this.goodsId = goodsId;
        this.activityRepository = activityRepository;
    }

    @Override
    public SkuVO call() throws Exception {
        return activityRepository.querySkuByGoodsId(goodsId);
    }

}
