package com.ls.community.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserStrategyFactory {

    @Autowired
    private List<UserStrategy> strategies;

    public UserStrategy getStrategy(String type){
        for (UserStrategy strategy : strategies) {
            if(Objects.equals(strategy.getSupportedType(), type))
                return strategy;
        }
        return null;
    }


}
