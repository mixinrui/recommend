package com.boxfishedu.recommend.core.common.upgrade;

import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficulty;
import com.boxfishedu.recommend.core.app.exception.DifficultyHighestException;
import com.boxfishedu.recommend.core.common.handler.LearningDifficultyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;


public class BeStateMachinePersist {

    private static Logger logger = LoggerFactory.getLogger(BeStateMachinePersist.class);

    @Autowired
    LearningDifficultyHandler learningDifficultyHandler;

    private PersistStateMachineHandler persistStateMachineHandler;


    public BeStateMachinePersist(PersistStateMachineHandler persistStateMachineHandler) {
        this.persistStateMachineHandler = persistStateMachineHandler;

        this.persistStateMachineHandler.addPersistStateChangeListener((state, message, transition, stateMachine) -> {
            if (message != null && message.getHeaders().containsKey("userId")) {
                Long userId = message.getHeaders().get("userId", Long.class);
                LearningDifficulty learningDifficulty = LearningDifficulty.valueOf(state.getId());

                learningDifficultyHandler.saveLearningDifficultyByUserId(userId, learningDifficulty);
                logger.debug("[boxfish-online-calculator] [addPersistStateChangeListener] 用户升级后学习难度. ID=[{}] DATA=[{}]", userId, learningDifficulty);

            } else {
                logger.error("[boxfish-online-calculator] [addPersistStateChangeListener] 学习难度升级异常.");
                throw new RuntimeException("学习难度升级异常.");
            }
        });
    }

    public void upgrade(Long userId) throws Exception {
        LearningDifficulty learningDifficulty = learningDifficultyHandler.getLearningDifficulty(userId);
        logger.debug("[boxfish-online-calculator] [upgrade] 用户升级前学习难度. ID=[{}] DATA=[{}]", userId, learningDifficulty);

        if (learningDifficulty.equals(LearningDifficulty.LEVEL_5)) {
            throw new DifficultyHighestException();
        }

        String event = "FROM_" + learningDifficulty;
        persistStateMachineHandler.handleEventWithState(MessageBuilder.withPayload(event).setHeader("userId", userId).build(), learningDifficulty.toString());
    }

}
