package com.zj.domain.activity.service.trial.factory;

import com.zj.domain.activity.model.entity.MarketProductEntity;
import com.zj.domain.activity.model.entity.TrialBalanceEntity;
import com.zj.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.zj.domain.activity.model.valobj.SkuVO;
import com.zj.domain.activity.service.trial.node.RootNode;
import com.zj.types.common.model.tree.handler.StrategyHandler;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultActivityStragyFactory {
    private final RootNode rootNode;

    public DefaultActivityStragyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> strategyHandler() {
        return rootNode;
    }


    @Data
    public static class DynamicContext{
        private GroupBuyActivityDiscountVO groupBuyActivityDiscountVO;
        private SkuVO skuVO;
        private BigDecimal deductionPrice;
        /**
         * 是否可见
         */
        private Boolean isVisible;

        /**
         * 是否可参与
         */
        private Boolean isEnable;

    }
}
