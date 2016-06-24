package com.boxfishedu.recommend.core.common.domain;

import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficulty;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface UserHistoryCounterRepository extends CrudRepository<UserHistoryCounterEntity, Long> {

    UserHistoryCounterEntity findByUserIdAndLearningDifficultyAndCourseDifficulty(Long userId, LearningDifficulty learningDifficulty, CourseDifficulty courseDifficulty);

    @Modifying
    @Transactional
    @Query("UPDATE UserHistoryCounterEntity u SET u.count = u.count + 1 WHERE u.userId = ?1 AND u.learningDifficulty = ?2 AND u.courseDifficulty = ?3")
    void increaseCounter(Long userId, LearningDifficulty learningDifficulty, CourseDifficulty courseDifficulty);
}
