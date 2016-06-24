package com.boxfishedu.recommend.provider.history.service;

import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.recommend.provider.history.domain.CourseMappingEntity;
import com.boxfishedu.recommend.provider.history.domain.CourseMappingRepository;
import com.boxfishedu.recommend.provider.history.domain.HistoryEntity;
import com.boxfishedu.recommend.provider.history.domain.HistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class HistoryService {

    private static Logger logger = LoggerFactory.getLogger(HistoryService.class);

    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    CourseMappingRepository courseMappingRepository;


    public Courses getHistory(Long userId) {
        Collection<HistoryEntity> historyEntities = historyRepository.findByUserId(userId);
        List<String> oldCourseIdList = new ArrayList<>();

        Courses result = new Courses();
        historyEntities.forEach(mysqlHistoryEntity -> {
            Course course = new Course();
            course.setCourseId(mysqlHistoryEntity.getLesson());
            result.add(course);

            oldCourseIdList.add(mysqlHistoryEntity.getLesson());
        });

        // 补充新课来自于老课的课程
        result.addAll(addNewCourses(oldCourseIdList));
        return result;
    }


    private Courses addNewCourses(List<String> oldCourseIdList) {
        Collection<CourseMappingEntity> courseMappingEntities = courseMappingRepository.findByOldCourseIdIn(oldCourseIdList);
        Set<String> set = courseMappingEntities.stream().map(CourseMappingEntity::getNewCourseId).collect(Collectors.toSet());

        Courses result = new Courses();
        set.forEach(courseId -> {
            Course course = new Course();
            course.setCourseId(courseId);

            result.add(course);
        });

        logger.debug("[boxfish-recommend-provider] [addNewCourses] 新老对照课程. DATA=[{}]", result);
        return result;
    }

}
