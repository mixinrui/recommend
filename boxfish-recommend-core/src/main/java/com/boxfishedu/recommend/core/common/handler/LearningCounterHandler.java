package com.boxfishedu.recommend.core.common.handler;

import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficulty;
import com.boxfishedu.recommend.core.common.domain.UserHistoryCounterEntity;
import com.boxfishedu.recommend.core.common.domain.UserHistoryCounterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class LearningCounterHandler {

    private static Logger logger = LoggerFactory.getLogger(LearningCounterHandler.class);

    @Autowired
    UserHistoryCounterRepository userLearnedCounterRepository;


    public Integer getCounter(Long userId, LearningDifficulty learningDifficulty, CourseDifficulty courseDifficulty) {

        Optional<UserHistoryCounterEntity> userLearnedCounterEntity = Optional.ofNullable(userLearnedCounterRepository.findByUserIdAndLearningDifficultyAndCourseDifficulty(userId, learningDifficulty, courseDifficulty));

        if (userLearnedCounterEntity.isPresent()) {
            return userLearnedCounterEntity.get().getCount();

        } else {
            // 为用户添加学习计数记录
            UserHistoryCounterEntity newUserLearnedCounterEntity = new UserHistoryCounterEntity();
            newUserLearnedCounterEntity.setUserId(userId);
            newUserLearnedCounterEntity.setLearningDifficulty(learningDifficulty);
            newUserLearnedCounterEntity.setCourseDifficulty(courseDifficulty);
            newUserLearnedCounterEntity.setCount(0);

            userLearnedCounterRepository.save(newUserLearnedCounterEntity);
            return newUserLearnedCounterEntity.getCount();
        }
    }


    @Autowired
    LearningDifficultyHandler learningDifficultyHandler;
    @Autowired
    CourseHandler courseHandler;


    public void increaseCounter(Long userId, String courseId) throws Exception {
        try {
            // 根据userId查询用户学习难度
            LearningDifficulty learningDifficulty = learningDifficultyHandler.getLearningDifficulty(userId);

            // 根据课程Id查询2个mongo,获得课程难度(假设课程只有1个难度)
            CourseDifficulty courseDifficulty = courseHandler.getCourseDifficulty(courseId);

            // 根据userId查询计数记录是否存在,如果没有则添加数据,如果有则更新数据
            Optional<UserHistoryCounterEntity> userLearnedCounterEntity = Optional.ofNullable(userLearnedCounterRepository.findByUserIdAndLearningDifficultyAndCourseDifficulty(userId, learningDifficulty, courseDifficulty));

            if (userLearnedCounterEntity.isPresent()) {
                // 自增
                userLearnedCounterRepository.increaseCounter(userId, learningDifficulty, courseDifficulty);

            } else {
                // 为用户添加学习计数记录
                UserHistoryCounterEntity newUserLearnedCounterEntity = new UserHistoryCounterEntity();
                newUserLearnedCounterEntity.setUserId(userId);
                newUserLearnedCounterEntity.setLearningDifficulty(learningDifficulty);
                newUserLearnedCounterEntity.setCourseDifficulty(courseDifficulty);
                newUserLearnedCounterEntity.setCount(1);

                userLearnedCounterRepository.save(newUserLearnedCounterEntity);
            }
        } catch (Exception e) {
            logger.error("[boxfish-recommend-core] [increaseCounter] 课程计数更新异常. EXCEPTION=[{}] ID=[{}] DATA=[{}]", e.toString(), userId, courseId);
        }

    }

}
