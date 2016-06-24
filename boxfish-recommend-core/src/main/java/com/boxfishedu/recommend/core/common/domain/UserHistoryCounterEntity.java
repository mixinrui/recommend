package com.boxfishedu.recommend.core.common.domain;


import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficulty;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(
        schema = "service",
        name = "recommend_learning_counter",
        indexes = {
                @Index(name = "index_group", columnList = "userId,learningDifficulty,courseDifficulty")}
)
public class UserHistoryCounterEntity {

    @Id
    @GeneratedValue
    Long id;
    Long userId;

    @Enumerated(EnumType.STRING)
    LearningDifficulty learningDifficulty;
    @Enumerated(EnumType.STRING)
    CourseDifficulty courseDifficulty;

    Integer count;

}