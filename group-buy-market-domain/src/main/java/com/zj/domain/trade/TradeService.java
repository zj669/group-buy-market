package com.zj.domain.trade;

import com.zj.domain.trade.adapter.repository.ITradeRepository;
import com.zj.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import com.zj.domain.trade.model.entity.MarketPayOrderEntity;
import com.zj.domain.trade.model.entity.PayActivityEntity;
import com.zj.domain.trade.model.entity.PayDiscountEntity;
import com.zj.domain.trade.model.entity.UserEntity;
import com.zj.domain.trade.model.valobj.GroupBuyProgressVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class TradeService implements ITradeService{
    @Resource
    private ITradeRepository tradeRepository;


    @Override
    public MarketPayOrderEntity queryNoPayMarketPayOouOrder(String userId, String outTradeNo) {
        return tradeRepository.queryNoPayMarketPayOouOrder(userId, outTradeNo);
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        return tradeRepository.queryGroupBuyProgress(teamId);
    }

    @Override
    public MarketPayOrderEntity lockPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity) {
        GroupBuyOrderAggregate groupBuyOrderAggregate = GroupBuyOrderAggregate.builder()
                .userEntity(userEntity)
                .payActivityEntity(payActivityEntity)
                .payDiscountEntity(payDiscountEntity)
                .build();
        return tradeRepository.lockPayOrder(groupBuyOrderAggregate);
    }
}
