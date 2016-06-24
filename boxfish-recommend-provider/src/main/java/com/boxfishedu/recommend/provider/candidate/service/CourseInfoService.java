package com.boxfishedu.recommend.provider.candidate.service;

import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.recommend.provider.candidate.domain.PrimaryCourseEntity;
import com.boxfishedu.recommend.provider.candidate.domain.PrimaryCourseRepository;
import com.boxfishedu.recommend.provider.candidate.domain.SecondaryCourseEntity;
import com.boxfishedu.recommend.provider.candidate.domain.SecondaryCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CourseInfoService {

    private static Logger logger = LoggerFactory.getLogger(CourseInfoService.class);

    @Autowired
    SecondaryCourseRepository candidateRepository;
    @Autowired
    PrimaryCourseRepository primaryCourseRepository;


    public Course getCourse(String courseId) {
        Optional<SecondaryCourseEntity> secondaryCourseEntity = Optional.ofNullable(candidateRepository.findByBookSectionId(courseId));
        if (secondaryCourseEntity.isPresent()) {
            return secondaryCourseEntity.get().toCourse();
        }

        Optional<PrimaryCourseEntity> primaryCourseEntity = Optional.ofNullable(primaryCourseRepository.findByBookSectionId(courseId));
        if (primaryCourseEntity.isPresent()) {
            return primaryCourseEntity.get().toCourse();
        }

        logger.error("[boxfish-recommend-provider] [getCourse] 课程信息查询异常. ID=[{}]", courseId);
        throw new RuntimeException("没有对应的课程信息.");
    }
}