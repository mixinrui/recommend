package com.boxfishedu.recommend.core.common.domain;


import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficulty;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(schema = "service", name = "recommend_learning_difficulty")
public class UserLearningDifficultyEntity {

    @Id
    Long userId;

    @Enumerated(EnumType.STRING)
    LearningDifficulty learningDifficulty;


    public UserLearningDifficultyEntity() {
    }

    public UserLearningDifficultyEntity(Long userId, LearningDifficulty learningDifficulty) {
        this.userId = userId;
        this.learningDifficulty = learningDifficulty;
    }

}
