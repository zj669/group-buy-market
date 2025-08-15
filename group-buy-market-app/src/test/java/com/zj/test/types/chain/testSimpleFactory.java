package com.zj.test.types.chain;

import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class testSimpleFactory extends AbstracSimpleChainFactory<String, DynamicContext, String>{
}
