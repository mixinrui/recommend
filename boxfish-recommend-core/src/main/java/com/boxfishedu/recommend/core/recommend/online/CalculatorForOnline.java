package com.boxfishedu.recommend.core.recommend.online;

import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficulty;
import com.boxfishedu.recommend.core.app.exception.DifficultyUpgradeException;
import com.boxfishedu.recommend.core.common.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
public class CalculatorForOnline {

    private static Logger logger = LoggerFactory.getLogger(OnlineTreeRoot.class);

    @Autowired
    CandidateHandler candidateHandler;
    @Autowired
    LearningDifficultyHandler learningDifficultyHandler;
    @Autowired
    HistoryHandler coursesHandler;
    @Autowired
    BufferHandler bufferHandler;
    @Autowired
    LearningCounterHandler learningCounterHandler;


    public Courses run(Long userId) throws Exception {
        OnlineTreeRoot treeRoot = getBeTreeRoot(userId);
        return treeRoot.takeAll();
    }

    private OnlineTreeRoot getBeTreeRoot(Long userId) throws Exception {

        LearningDifficulty learningDifficulty = learningDifficultyHandler.getLearningDifficulty(userId);
        logger.debug("[boxfish-online-calculator] [run] 获取学习难度. ID=[{}] DATA=[{}]", userId, learningDifficulty);

        // 检测升级条件
        checkUpgradeForProgress(userId, learningDifficulty);

        Map<String, String> httpHeads = new HashMap<>();
        Courses coursesAll = candidateHandler.getCoursesByLearningDifficulty(learningDifficulty, httpHeads);
        logger.debug("[boxfish-online-calculator] [run] 获取可学课程. ID=[{}] DATA=[{}]", userId, coursesAll);

        Courses coursesLearned = coursesHandler.getLearnedCourses(userId);
        logger.debug("[boxfish-online-calculator] [run] 获取学过课程. ID=[{}] DATA=[{}]", userId, coursesLearned);

        Courses coursesCache = bufferHandler.getCourses(userId);
        logger.debug("[boxfish-online-calculator] [run] 获取缓存课程. ID=[{}] DATA=[{}]", userId, coursesCache);

        // 过滤学过的课程
        Courses coursesFiltered = coursesAll.filter(coursesLearned).filter(coursesCache);

        // 加载数据
        OnlineTreeRoot treeRoot = new OnlineTreeRoot();
        treeRoot.load(coursesFiltered);
        return treeRoot;
    }

    public Course getByIndex(Long userId, Integer index) throws Exception {
        OnlineTreeRoot treeRoot = getBeTreeRoot(userId);
        Optional<Course> courseOptional = treeRoot.takeOne(index);
        if (!courseOptional.isPresent()) {
            logger.warn("课程数量不足.启动升级");
            throw new DifficultyUpgradeException("课程数量不足");
        }
        return courseOptional.get();
    }

    private void checkUpgradeForProgress(Long userId, LearningDifficulty learningDifficulty) throws DifficultyUpgradeException {
        Integer threshold = LearningDifficulty.getUpgradeThresholdPerCourseDifficulty(learningDifficulty);

        for (CourseDifficulty courseDifficulty : LearningDifficulty.getCourseDifficultyList(learningDifficulty)) {
            if (learningCounterHandler.getCounter(userId, learningDifficulty, courseDifficulty) >= threshold) {
                throw new DifficultyUpgradeException("完成课程满足升级条件.");
            }
        }
    }

}
