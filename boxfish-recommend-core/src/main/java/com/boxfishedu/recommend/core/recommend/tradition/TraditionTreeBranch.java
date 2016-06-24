package com.boxfishedu.recommend.core.recommend.tradition;


import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.component.boxfish.protocal.enums.CourseType;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TraditionTreeBranch {

    private static Logger logger = LoggerFactory.getLogger(TraditionTreeBranch.class);

    private static List<CourseType> ruleA = Lists.newArrayList(CourseType.GRAMMAR, CourseType.FUNCTION, CourseType.READING, CourseType.CONVERSATION);
    private static List<CourseType> ruleB = Lists.newArrayList(CourseType.GRAMMAR, CourseType.FUNCTION, CourseType.READING);
    private static List<CourseType> ruleC = Lists.newArrayList(CourseType.FUNCTION);

    private Map<CourseType, TraditionCourseSet> treeBranch = new ConcurrentHashMap<>();
    private Courses resultBuffer = new Courses();


    public TraditionTreeBranch() {
        treeBranch.put(CourseType.GRAMMAR, new TraditionCourseSet());
        treeBranch.put(CourseType.FUNCTION, new TraditionCourseSet());
        treeBranch.put(CourseType.READING, new TraditionCourseSet());
        treeBranch.put(CourseType.CONVERSATION, new TraditionCourseSet());
    }

    public void load(Course course) {
        switch (course.getCourseType()) {
            case GRAMMAR:
                course.setInnerOrder(course.getBundleOrder());
                treeBranch.get(CourseType.GRAMMAR).put(course);
                break;

            case FUNCTION:
                course.setInnerOrder((int) (Math.random() * 10000));
                treeBranch.get(CourseType.FUNCTION).put(course);
                break;

            case READING:
                course.setInnerOrder((int) (Math.random() * 10000));
                treeBranch.get(CourseType.READING).put(course);
                break;

            case CONVERSATION:
                course.setInnerOrder((int) (Math.random() * 10000));
                treeBranch.get(CourseType.CONVERSATION).put(course);
                break;

            default:
                logger.warn("[boxfish-online-calculator] [load] 类型匹配异常. DATA=[{}]]", course);
        }
    }

    public Courses takeOnce() {
        Courses result = new Courses();

        treeBranch.forEach((courseType, courseSet) -> {
            if (!courseSet.checkEmpty() && ruleA.contains(courseType)) {
                result.add(courseSet.pop());
            }
        });

        treeBranch.forEach((courseType, courseSet) -> {
            if (!courseSet.checkEmpty() && ruleB.contains(courseType)) {
                result.add(courseSet.pop());
            }
        });

        treeBranch.forEach((courseType, courseSet) -> {
            if (!courseSet.checkEmpty() && ruleC.contains(courseType)) {
                result.add(courseSet.pop());
            }
        });

        return result;
    }

    public Courses takeAll() {
        Courses result = new Courses();
        while (true) {
            Courses courses = takeOnce();
            if (courses.checkEmpty()) {
                break;
            } else {
                result.addAll(courses);
            }
        }
        return result;
    }

    public void loadResultBuffer() {
        resultBuffer.addAll(takeAll());
    }

    public Courses getResultBuffer() {
        return resultBuffer;
    }

}
