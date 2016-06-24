package com.boxfishedu.recommend.provider.candidate.service;

import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.protocal.enums.CourseType;
import com.boxfishedu.component.boxfish.util.string.FormatUtil;
import com.boxfishedu.recommend.provider.candidate.domain.SecondaryCourseEntity;
import com.boxfishedu.recommend.provider.candidate.domain.SecondaryCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class RecommendService {

    private static Logger logger = LoggerFactory.getLogger(RecommendService.class);

    @Autowired
    SecondaryCourseRepository secondaryCourseRepository;


    public Courses getCandidateCourses(List<CourseDifficulty> courseDifficultyList) {
        Collection<SecondaryCourseEntity> secondaryCourseEntities = secondaryCourseRepository.findByDifficultyInAndRecommend(createQueryParameter(courseDifficultyList), true);

        logger.debug("[boxfish-recommend-provider] [getCandidateCourses] Mongo课程数量: DATA=[{}]",
                FormatUtil.toJsonNoException(courseDifficultyList) + "->" + secondaryCourseEntities.size());

        Courses result = createResult(secondaryCourseEntities);

        logger.debug("[boxfish-recommend-provider] [getCandidateCourses] 可推荐课程数量. DATA=[{}]",
                FormatUtil.toJsonNoException(courseDifficultyList) + "->" + result.size());

        return result;
    }

    private List<String> createQueryParameter(Collection<CourseDifficulty> courseDifficulties) {
        List<String> parameter = new ArrayList<>();
        courseDifficulties.forEach(courseDifficulty -> parameter.add(courseDifficulty.getIndex()));
        return parameter;
    }

    private Courses createResult(Collection<SecondaryCourseEntity> secondaryCourseEntities) {
        Courses result = new Courses();
        secondaryCourseEntities.forEach(courseEntity -> {
            try {
                Course course = courseEntity.toCourse();
                result.add(course);
            } catch (Exception e) {
                logger.error("[boxfish-recommend-provider] [createResult] Mongo数据异常. EXCEPTION=[{}] DATA=[{}]", e.toString(), courseEntity);
            }
        });
        return result;
    }

    public Courses getDefaultCourses() {
        Collection<SecondaryCourseEntity> mongoCourseEntities = secondaryCourseRepository.findByCourseTypeIgnoreCaseAndRecommend(CourseType.GRAMMAR.name(), true);
        return createResult(mongoCourseEntities);
    }

}