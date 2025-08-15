package com.zj.domain.activity.service.trial.node;

import com.alibaba.fastjson2.JSON;
import com.zj.domain.activity.model.entity.MarketProductEntity;
import com.zj.domain.activity.model.entity.TrialBalanceEntity;
import com.zj.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.types.design.tree.handler.StrategyHandler;
import com.zj.types.enums.ResponseCode;
import com.zj.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Slf4j
@Service
public class RootNode extends AbstractGroupBuyMarketSupport {
    @Resource
    private SwitchNode switchNode;

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParams, DynamicContext context) {
        log.info("进来root节点，用户id为 %s， 参数为： %s".formatted(requestParams.getUserId(), JSON.toJSONString(requestParams)));
        if (StringUtils.isBlank(requestParams.getUserId()) || StringUtils.isBlank(requestParams.getGoodsId())) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }
        return route(requestParams, context);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> getStrategyHandler(MarketProductEntity requestParams, DynamicContext context) {
        return switchNode;
    }
}
