package com.zj.domain.trade.service.lock;

import com.zj.domain.trade.adapter.repository.ITradeRepository;
import com.zj.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import com.zj.domain.trade.model.entity.MarketPayOrderEntity;
import com.zj.domain.trade.model.entity.PayActivityEntity;
import com.zj.domain.trade.model.entity.PayDiscountEntity;
import com.zj.domain.trade.model.entity.TradeRuleCommandEntity;
import com.zj.domain.trade.model.entity.TradeRuleFilterBackEntity;
import com.zj.domain.trade.model.entity.UserEntity;
import com.zj.domain.trade.model.valobj.GroupBuyProgressVO;
import com.zj.domain.trade.service.ITradeLockService;
import com.zj.domain.trade.service.fillter.factory.TradeRuleFilterFactory;
import com.zj.domain.trade.service.fillter.factory.TradeRuleFilterFactory.DynamicContext;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class TradeLockService implements ITradeLockService {
    @Resource
    private ITradeRepository tradeRepository;
    @Resource
    private TradeRuleFilterFactory tradeRuleFilterFactory;


    @Override
    public MarketPayOrderEntity queryNoPayMarketPayOouOrder(String userId, String outTradeNo) {
        log.info("拼团交易-查询未支付营销订单:{} outTradeNo:{}", userId, outTradeNo);
        return tradeRepository.queryNoPayMarketPayOouOrder(userId, outTradeNo);
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        log.info("拼团交易-查询拼单进度:{}", teamId);
        return tradeRepository.queryGroupBuyProgress(teamId);
    }

    @Override
    public MarketPayOrderEntity lockPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity) {
        log.info("拼团交易-锁定营销优惠支付订单:{} activityId:{} goodsId:{}", userEntity.getUserId(), payActivityEntity.getActivityId(), payDiscountEntity.getGoodsId());
        AbstracSimpleChainModel<TradeRuleCommandEntity, DynamicContext, TradeRuleFilterBackEntity> chain = tradeRuleFilterFactory.getChain();
        // 交易规则过滤
        TradeRuleFilterBackEntity tradeRuleFilterBackEntity = chain.handle(TradeRuleCommandEntity.builder()
                        .activityId(payActivityEntity.getActivityId())
                        .userId(userEntity.getUserId())
                        .build(),
                new TradeRuleFilterFactory.DynamicContext());

        // 已参与拼团量 - 用于构建数据库唯一索引使用，确保用户只能在一个活动上参与固定的次数
        Integer userTakeOrderCount = tradeRuleFilterBackEntity.getUserTakeOrderCount();
        log.info("用户已参与拼团量:{}", userTakeOrderCount);
        GroupBuyOrderAggregate groupBuyOrderAggregate = GroupBuyOrderAggregate.builder()
                .userEntity(userEntity)
                .payActivityEntity(payActivityEntity)
                .payDiscountEntity(payDiscountEntity)
                .userTakeOrderCount(userTakeOrderCount)
                .build();
        return tradeRepository.lockPayOrder(groupBuyOrderAggregate);
    }
}
