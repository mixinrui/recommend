package com.boxfishedu.recommend.provider.collector.web;

import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.protocal.enums.CourseType;
import com.boxfishedu.recommend.provider.Application;
import com.boxfishedu.recommend.provider.candidate.service.RecommendService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
public class CourseControllerTest {

    @Autowired
    RecommendService candidateService;

    List<CourseDifficulty> courseDifficulties = Lists.newArrayList(CourseDifficulty.values());

    /**
     * 统计不同学习难度,各个课程类别的课程数量
     */
    @Test
    public void testCourseController() throws Exception {

        courseDifficulties.forEach(courseDifficulty -> {
            Courses courses = candidateService.getCandidateCourses(Lists.newArrayList(courseDifficulty));

            Map<CourseType, Courses> map = createMap();
            courses.foreach(course -> map.get(course.getCourseType()).add(course));

            System.out.println("难度级别:" + courseDifficulty);
            map.forEach((k, v) -> System.out.println(k + ": " + v.size()));
        });

    }

    public Map<CourseType, Courses> createMap() {
        Map<CourseType, Courses> map = new ConcurrentHashMap<>();

        List<CourseType> courseTypes = Lists.newArrayList(CourseType.values());
        courseTypes.forEach(courseType -> map.put(courseType, new Courses()));

        return map;
    }

}