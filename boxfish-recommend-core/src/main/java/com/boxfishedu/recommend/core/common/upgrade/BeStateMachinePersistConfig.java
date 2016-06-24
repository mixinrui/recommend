package com.boxfishedu.recommend.core.common.upgrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;


@Configuration
public class BeStateMachinePersistConfig {

    @SuppressWarnings("all")
    @Autowired
    private StateMachine<String, String> stateMachine;

    @Bean
    public PersistStateMachineHandler persistStateMachineHandler() {
        return new PersistStateMachineHandler(stateMachine);
    }

    @Bean
    public BeStateMachinePersist persist() {
        return new BeStateMachinePersist(persistStateMachineHandler());
    }
}
