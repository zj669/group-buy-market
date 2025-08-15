package com.zj.types.design.linke.simpleChain;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AbstracSimpleChainFactory<T, D, R> {
    // todo 还需要实现一个自定义组装的方法
    @Resource
    private List<AbstracSimpleChainModel<T, D, R>> chainModelList;

    public AbstracSimpleChainModel<T, D, R> getChain(){
        if(chainModelList.isEmpty() || chainModelList.size() == 0){
            return null;
        }
        for(int i=0;i+1<chainModelList.size();i++){
            chainModelList.get(i).setNextModel(chainModelList.get(i+1));
        }
        return chainModelList.get(0);
    }
}
