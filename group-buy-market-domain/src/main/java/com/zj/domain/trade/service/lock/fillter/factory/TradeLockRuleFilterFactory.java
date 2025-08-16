package com.zj.domain.trade.service.lock.fillter.factory;

import com.zj.domain.trade.model.entity.GroupBuyActivityEntity;
import com.zj.domain.trade.model.entity.TradeLockRuleCommandEntity;
import com.zj.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainFactory;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TradeLockRuleFilterFactory extends AbstracSimpleChainFactory<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity>  {
    @Resource
    private List<AbstracSimpleChainModel<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity>> modelList;

    @Override
    public void setChainModelList() {
        this.chainModelList = modelList;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        private GroupBuyActivityEntity groupBuyActivity;

    }
}
