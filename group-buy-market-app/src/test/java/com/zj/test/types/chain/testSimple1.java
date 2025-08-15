package com.zj.test.types.chain;

import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
public class testSimple1 extends AbstracSimpleChainModel<String,DynamicContext,String> {
    @Override
    protected String apply(String s, DynamicContext dynamicContext) {
        log.info("进入单例节点1");
        return "";
    }
}
