package com.boxfishedu.recommend.core.buffer.service;

import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.component.boxfish.protocal.enums.RecommendChannel;
import com.boxfishedu.recommend.core.common.domain.UserBufferEntity;
import com.boxfishedu.recommend.core.common.domain.UserBufferRepository;
import com.boxfishedu.recommend.core.recommend.api.RecommendForOnlineService;
import com.boxfishedu.recommend.core.recommend.api.RecommendForTraditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;


@Service
public class UserBufferService {

    private static Logger logger = LoggerFactory.getLogger(UserBufferService.class);

    @Autowired
    UserBufferRepository bufferRepository;

    @Autowired
    RecommendForTraditionService recommendForTraditionService;
    @Autowired
    RecommendForOnlineService recommendForOnlineService;


    public void clearCourse(Long userId) {
        bufferRepository.deleteByUserId(userId);
        logger.debug("[boxfish-recommend-core] [clearCourse] 清除缓存课程. ID=[{}]", userId);
    }

    public void deleteCourse(Long userId, String courseId) {
        bufferRepository.deleteByUserIdAndCourseId(userId, courseId);
        logger.debug("[boxfish-recommend-core] [deleteCourse] 删除缓存课程. ID=[{}] DATA=[{}]", userId, courseId);
    }

    public Courses findCoursesForTradition(Long userId) {
        Courses result = new Courses();

        Collection<UserBufferEntity> bufferEntities = bufferRepository.findByUserIdAndRecommendChannelOrderByIdAsc(userId, RecommendChannel.STUDENT);
        bufferEntities.forEach(bufferEntity -> {
            Course course = bufferEntity.toCourse();
            result.add(course);
        });
        return result;
    }

    public void recommendCoursesForTradition(Long userId, Map<String, String> httpHeads) throws Exception {
        Courses courses = recommendForTraditionService.recommendCourses(userId, httpHeads);
        saveCourses(userId, RecommendChannel.STUDENT, courses);
    }

    @Async("BeAsyncExecutor")
    public void recommendCoursesForTraditionAsync(Long userId, Map<String, String> httpHeads) {
        try {
            recommendCoursesForTradition(userId, httpHeads);
        } catch (Exception e) {
            logger.error("[boxfish-recommend-core] [recommendCoursesForTraditionAsync] 异步推荐课程异常. EXCEPTION=[{}] ID=[{}]", e.toString(), userId);
        }
    }

    public Course recommendCoursesForOnline(Long userId, Integer index) throws Exception {
        Course course = recommendForOnlineService.recommendCourse(userId, index);
        saveCourse(userId, RecommendChannel.ONLINE, course);
        return course;
    }

    private void saveCourse(Long userId, RecommendChannel recommendChannel, Course course) {
        UserBufferEntity bufferEntity = new UserBufferEntity();
        bufferEntity.setUserId(userId);
        bufferEntity.setRecommendChannel(recommendChannel);

        bufferEntity.fromCourse(course);

        bufferRepository.save(bufferEntity);
    }

    private void saveCourses(Long userId, RecommendChannel recommendChannel, Courses courses) {
        courses.foreach(course -> saveCourse(userId, recommendChannel, course));
    }
}
