package com.boxfishedu.recommend.core.common.handler;


import com.boxfishedu.component.boxfish.protocal.domain.Preference;
import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficulty;
import com.boxfishedu.recommend.core.common.domain.UserLearningDifficultyEntity;
import com.boxfishedu.recommend.core.common.domain.UserLearningDifficultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LearningDifficultyHandler {

    @Autowired
    PreferenceHandler preferenceHandler;

    @Autowired
    UserLearningDifficultyRepository userLearningDifficultyRepository;


    public LearningDifficulty getLearningDifficulty(Long userId) throws Exception {
        Optional<UserLearningDifficultyEntity> userLearningDifficultyEntity = Optional.ofNullable(userLearningDifficultyRepository.findOne(userId));

        if (userLearningDifficultyEntity.isPresent()) {
            return userLearningDifficultyEntity.get().getLearningDifficulty();

        } else {
            // 将偏好难度转换为学习难度
            Preference preference = preferenceHandler.getPreference(userId);
            LearningDifficulty learningDifficulty = LearningDifficulty.valueOf(preference.getPreferenceDifficulty().toString());

            // 为用户添加学习难度记录
            UserLearningDifficultyEntity newUserLearningDifficultyEntity = new UserLearningDifficultyEntity(userId, learningDifficulty);
            userLearningDifficultyRepository.save(newUserLearningDifficultyEntity);

            return newUserLearningDifficultyEntity.getLearningDifficulty();
        }
    }

    public void saveLearningDifficultyByUserId(Long userId, LearningDifficulty learningDifficulty) {
        UserLearningDifficultyEntity userLearningDifficultyEntity = new UserLearningDifficultyEntity();
        userLearningDifficultyEntity.setUserId(userId);
        userLearningDifficultyEntity.setLearningDifficulty(learningDifficulty);
        userLearningDifficultyRepository.save(userLearningDifficultyEntity);
    }

}
