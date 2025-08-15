package com.zj.test.types.chain;

import com.zj.domain.activity.service.trial.factory.DefaultActivityStragyFactory.DynamicContext;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainFactory;
import com.zj.types.design.linke.simpleChain.AbstracSimpleChainModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChainTypesTest {
    @Resource
    private AbstracSimpleChainFactory<String, DynamicContext, String> testSimpleFactory;
    @Test
    public void testSimpleChain() {
        AbstracSimpleChainModel<String, DynamicContext, String> chain = testSimpleFactory.getChain();
        String nnn = chain.handle("nnn", new DynamicContext());
        System.out.println(nnn);
    }
}
