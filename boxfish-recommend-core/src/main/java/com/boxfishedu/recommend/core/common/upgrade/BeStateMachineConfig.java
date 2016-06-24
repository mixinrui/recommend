package com.boxfishedu.recommend.core.common.upgrade;


import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficulty;
import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficultyUpgradeEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;


@Configuration
@EnableStateMachine(contextEvents = false)
public class BeStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states
                .withStates()
                .initial(LearningDifficulty.LEVEL_1.toString())
                .end(LearningDifficulty.LEVEL_5.toString())
                .states(LearningDifficulty.toSet());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions
                .withExternal()
                .source(LearningDifficulty.LEVEL_1.toString()).target(LearningDifficulty.LEVEL_1P.toString())
                .event(LearningDifficultyUpgradeEvent.FROM_LEVEL_1.toString())
                .and()

                .withExternal()
                .source(LearningDifficulty.LEVEL_1P.toString()).target(LearningDifficulty.LEVEL_2.toString())
                .event(LearningDifficultyUpgradeEvent.FROM_LEVEL_1P.toString())
                .and()

                .withExternal()
                .source(LearningDifficulty.LEVEL_2.toString()).target(LearningDifficulty.LEVEL_2P.toString())
                .event(LearningDifficultyUpgradeEvent.FROM_LEVEL_2.toString())
                .and()

                .withExternal()
                .source(LearningDifficulty.LEVEL_2P.toString()).target(LearningDifficulty.LEVEL_3.toString())
                .event(LearningDifficultyUpgradeEvent.FROM_LEVEL_2P.toString())
                .and()

                .withExternal()
                .source(LearningDifficulty.LEVEL_3.toString()).target(LearningDifficulty.LEVEL_3P.toString())
                .event(LearningDifficultyUpgradeEvent.FROM_LEVEL_3.toString())
                .and()

                .withExternal()
                .source(LearningDifficulty.LEVEL_3P.toString()).target(LearningDifficulty.LEVEL_4.toString())
                .event(LearningDifficultyUpgradeEvent.FROM_LEVEL_3P.toString())
                .and()

                .withExternal()
                .source(LearningDifficulty.LEVEL_4.toString()).target(LearningDifficulty.LEVEL_4P.toString())
                .event(LearningDifficultyUpgradeEvent.FROM_LEVEL_4.toString())
                .and()

                .withExternal()
                .source(LearningDifficulty.LEVEL_4P.toString()).target(LearningDifficulty.LEVEL_5.toString())
                .event(LearningDifficultyUpgradeEvent.FROM_LEVEL_4P.toString());
    }

}