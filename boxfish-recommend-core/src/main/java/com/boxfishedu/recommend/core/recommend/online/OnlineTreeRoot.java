package com.boxfishedu.recommend.core.recommend.online;


import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.component.boxfish.protocal.enums.CourseType;
import com.boxfishedu.recommend.core.app.exception.DifficultyUpgradeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class OnlineTreeRoot {

    private static Logger logger = LoggerFactory.getLogger(OnlineTreeRoot.class);

    private Map<CourseType, OnlineTreeBranch> tree = OnlineTreeRootConfig.rule.getTree();


    public void load(Courses courses) {
        courses.foreach(course -> {
            try {
                if (course != null) {
                    if (tree.containsKey(course.getCourseType())) {
                        tree.get(course.getCourseType()).put(course);
                    }
                }
            } catch (Exception e) {
                logger.error("课程信息异常" + course);
            }

        });
    }


    public Optional<Course> takeOne(Integer index) {
        List<CourseType> list = OnlineRecommendRuleConfig.rule.getTypesByIndex(index);
        for (CourseType beCourseType : list) {
            Optional<Course> optional = tree.get(beCourseType).pop();
            if (optional.isPresent()) {
                return optional;
            }
        }
        return Optional.empty();
    }

    public Courses takeAll() throws DifficultyUpgradeException {
        Courses beCourses = new Courses();
        for (int i = 1; i <= OnlineTreeRootConfig.size; i++) {
            if (!takeOne(i).isPresent()) {
                logger.warn("课程数量不足.启动升级");
                throw new DifficultyUpgradeException("课程数量不足");
            }
            beCourses.add(takeOne(i).get());
        }
        return beCourses;
    }

}
