package com.zj.types.common.model.tree;


import com.zj.types.common.model.tree.handler.DefaultStrategyHandler;
import com.zj.types.common.model.tree.handler.StrategyHandler;
import lombok.Data;


@Data
public abstract class AbstractStrategyRouter<T, D, R> implements StrategyMapper<T, D, R>, StrategyHandler<T, D, R> {

    private DefaultStrategyHandler<T, D, R> defaultStrategyHandler;
    public  R route(T t, D d){
        StrategyHandler<T, D, R> strategyHandler = getStrategyHandler(t, d);
        if(strategyHandler != null){
            return strategyHandler.apply(t, d);
        }
        return defaultStrategyHandler.apply(t, d);
    };
}
