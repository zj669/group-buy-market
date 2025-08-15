package com.zj.domain.trade.adapter.repository;

import com.zj.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import com.zj.domain.trade.model.entity.GroupBuyActivityEntity;
import com.zj.domain.trade.model.entity.MarketPayOrderEntity;
import com.zj.domain.trade.model.valobj.GroupBuyProgressVO;
import org.springframework.stereotype.Repository;

@Repository
public interface ITradeRepository {
    MarketPayOrderEntity queryNoPayMarketPayOouOrder(String userId, String outTradeNo);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    MarketPayOrderEntity lockPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    GroupBuyActivityEntity queryGroupBuyActivityEntityByActivityId(Long activityId);

    Integer queryOrderCountByActivityId(Long activityId, String userId);
}
