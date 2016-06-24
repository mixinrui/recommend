package com.boxfishedu.recommend.core.recommend.tradition;


import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficulty;
import com.boxfishedu.recommend.core.app.exception.DifficultyEmptyException;
import com.boxfishedu.recommend.core.app.exception.DifficultyUpgradeException;
import com.boxfishedu.recommend.core.common.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class CalculatorForTradition {

    private static Logger logger = LoggerFactory.getLogger(CalculatorForTradition.class);
    private static Integer RECOMMEND_SIZE = 10;

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


    public Courses run(Long userId, Map<String, String> httpHeads) throws Exception {
        logger.debug("[boxfish-online-calculator] [run] 课程推荐请求. ID=[{}] DATA=[{}]", userId);

        LearningDifficulty learningDifficulty = learningDifficultyHandler.getLearningDifficulty(userId);
        logger.debug("[boxfish-online-calculator] [run] 获取学习难度. ID=[{}] DATA=[{}]", userId, learningDifficulty);

        // 检测学习进度升级条件
        checkUpgradeForProgress(userId, learningDifficulty);

        Courses coursesAll = candidateHandler.getCoursesByLearningDifficulty(learningDifficulty, httpHeads);
        logger.debug("[boxfish-online-calculator] [run] 获取可学课程. ID=[{}] DATA=[{}]", userId, coursesAll);

        Courses coursesLearned = coursesHandler.getLearnedCourses(userId);
        logger.debug("[boxfish-online-calculator] [run] 获取学过课程. ID=[{}] DATA=[{}]", userId, coursesLearned);

        Courses coursesCache = bufferHandler.getCourses(userId);
        logger.debug("[boxfish-online-calculator] [run] 获取缓存课程. ID=[{}] DATA=[{}]", userId, coursesCache);

        Courses result = run(learningDifficulty, coursesAll, coursesLearned, coursesCache);
        logger.debug("[boxfish-online-calculator] [run] 推荐课程内容. ID=[{}] DATA=[{}]", userId, result);
        return result;
    }

    public Courses run(LearningDifficulty learningDifficulty, Courses coursesAll, Courses coursesLearned, Courses coursesCache) throws DifficultyEmptyException {

        // 过滤课程
        Courses coursesFiltered = coursesAll.filter(coursesLearned).filter(coursesCache);

        // 填充结构
        TraditionTreeRoot treeRoot = new TraditionTreeRoot();
        treeRoot.load(coursesFiltered).loadResultBuffer();

        // 检测升级条件: 如果相关课程难度的课程数量小于需求数量
        Integer courseDifficultySize = LearningDifficulty.getCourseDifficultyListSize(learningDifficulty);
        Integer courseMinSize = Math.floorDiv(RECOMMEND_SIZE, courseDifficultySize);

        // 推荐课程
        Courses result = new Courses();
        for (CourseDifficulty courseDifficulty : LearningDifficulty.getCourseDifficultyList(learningDifficulty)) {
            checkUpgradeForCourseStock(treeRoot.sizeOfResultBuffer(courseDifficulty), courseMinSize);
            result.addAll(treeRoot.takeFromResultBuffer(courseDifficulty, courseMinSize));
        }

        return result;
    }

    private void checkUpgradeForProgress(Long userId, LearningDifficulty learningDifficulty) throws DifficultyUpgradeException {
        Integer threshold = LearningDifficulty.getUpgradeThresholdPerCourseDifficulty(learningDifficulty);

        for (CourseDifficulty courseDifficulty : LearningDifficulty.getCourseDifficultyList(learningDifficulty)) {
            if (learningCounterHandler.getCounter(userId, learningDifficulty, courseDifficulty) >= threshold) {
                throw new DifficultyUpgradeException("完成课程数量满足升级条件.");
            }
        }
    }

    private void checkUpgradeForCourseStock(Integer stock, Integer target) throws DifficultyEmptyException {
        if (stock < target) {
            throw new DifficultyEmptyException("课程数量不足.");
        }
    }
}
