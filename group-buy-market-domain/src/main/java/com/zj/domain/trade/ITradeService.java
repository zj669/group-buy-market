package com.zj.domain.trade;

import com.zj.domain.trade.model.entity.MarketPayOrderEntity;
import com.zj.domain.trade.model.entity.PayActivityEntity;
import com.zj.domain.trade.model.entity.PayDiscountEntity;
import com.zj.domain.trade.model.entity.UserEntity;
import com.zj.domain.trade.model.valobj.GroupBuyProgressVO;

public interface ITradeService {

    MarketPayOrderEntity queryNoPayMarketPayOouOrder(String userId, String outTradeNo);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    MarketPayOrderEntity lockPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity);
}
