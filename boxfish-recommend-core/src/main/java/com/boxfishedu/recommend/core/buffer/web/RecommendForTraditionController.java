package com.boxfishedu.recommend.core.buffer.web;

import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.component.boxfish.util.bean.BeanToJson;
import com.boxfishedu.component.boxfish.util.string.FormatUtil;
import com.boxfishedu.recommend.core.app.exception.DifficultyEmptyException;
import com.boxfishedu.recommend.core.buffer.service.UserBufferService;
import com.boxfishedu.recommend.core.buffer.service.UserFlagSkipService;
import com.boxfishedu.recommend.core.common.handler.AccessTokenHandler;
import com.boxfishedu.recommend.core.common.handler.SkipReportHandler;
import com.boxfishedu.recommend.core.recommend.api.RecommendForTraditionService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;


@RestController
@RequestMapping(value = "/recommend/core")
public class RecommendForTraditionController {

    private static Logger logger = LoggerFactory.getLogger(RecommendForTraditionController.class);

    @Autowired
    AccessTokenHandler accessTokenHandler;

    @Autowired
    UserBufferService bufferService;
    @Autowired
    UserFlagSkipService userFlagSkipService;

    @Autowired
    RecommendForTraditionService recommendForTraditionService;
    @Autowired
    SkipReportHandler skipReportHandler;


    @RequestMapping(value = "/tradition", method = RequestMethod.POST)
    public Object traditionRecommend(@RequestParam(value = "lesson_id", required = false) String courseId,
                                     @RequestParam(value = "is_skipped", defaultValue = "false") Boolean isSkipped,
                                     @RequestHeader HttpHeaders headers,
                                     String access_token) throws Exception {

        Map<String, String> httpHeads = createHttpHeads(headers);


        logger.info("[boxfish-recommend-core] [traditionRecommend] 课程推荐请求. ID=[{}] DATA=[{}]", access_token,
                "courseId=" + courseId + " isSkipped=" + isSkipped + " head=" + FormatUtil.toJsonNoException(headers));

        Long userId = accessTokenHandler.getUserId(access_token);
        logger.info("[boxfish-recommend-core] [traditionRecommend] 课程推荐请求. ID=[{}] DATA=[{}]", userId, access_token);


        if (isSkipped) {
            // 跳过课程

            if (userFlagSkipService.checkFlagTrue(userId)) {
                // 前一次跳过课程
                return LessonsForApp.buildWarnNoSkip().toJson();

            } else {
                // 前一次完成课程
                if (isNotBlank((courseId))) {
                    bufferService.deleteCourse(userId, courseId);

                    // 课程上报主服务
                    skipReportHandler.doReport(access_token, courseId);

                    // 开启跳过标记
                    userFlagSkipService.setFlagTrue(userId);
                }
            }
        } else {
            // 完成课程
            if (isNotBlank((courseId))) {
                bufferService.deleteCourse(userId, courseId);

                // 关闭跳过标记
                userFlagSkipService.setFlagFalse(userId);
            }
        }


        try {
            String result = createResult(userId, httpHeads);
            logger.info("[boxfish-recommend-core] [traditionRecommend] 课程推荐应答. ID=[{}] DATA=[{}]", userId, result);
            return result;

        } catch (DifficultyEmptyException e) {
            logger.warn("[boxfish-recommend-core] [traditionRecommend] 课程推荐应答. ID=[{}] DATA=[{}]", userId, "课程数量不足");
            return ResponseEntity.notFound().build();
        }
    }

    private Map<String, String> createHttpHeads(HttpHeaders headers) {
        Map<String, String> result = new HashMap<>();

        String headValue = headers.getFirst("x-be-product");
        if (headValue != null) {
            result.put("x-be-product", headValue);
        }
        return result;
    }

    private String createResult(Long userId, Map<String, String> httpHeads) throws Exception {
        Courses preResult = bufferService.findCoursesForTradition(userId);

        if (preResult.size() == 0) {
            // 如果缓存课程为空,则做同步推荐,如果课程数量不足则抛出"课程数量不足"异常
            bufferService.recommendCoursesForTradition(userId, httpHeads);

            Courses result = bufferService.findCoursesForTradition(userId);
            return LessonsForApp.buildByCourses(result).toJson();

        } else if (preResult.size() < 5) {
            // 如果缓存课程小于阈值,则做异步推荐,异步推荐中隐藏"课程数量不足"异常
            bufferService.recommendCoursesForTraditionAsync(userId, httpHeads);

            return LessonsForApp.buildByCourses(preResult).toJson();

        } else {
            // 如果缓存课程数量充足,则直接返回缓存课程
            return LessonsForApp.buildByCourses(preResult).toJson();
        }

    }

    // 适配App端通信协议
    @Data
    private static class LessonForApp implements BeanToJson {
        private String id;
        private String name;
        private String type;
        private String difficulty;
        private String cover;
        private Integer price;
        private Long lastModified;
    }

    @Data
    private static class LessonsForApp implements BeanToJson {

        private List<LessonForApp> courses = new ArrayList<>();

        private Boolean not_skipped;
        private String not_skipped_msg;


        public static LessonsForApp buildWarnNoSkip() {
            LessonsForApp lessonsForApp = new LessonsForApp();
            lessonsForApp.setNot_skipped(true);
            lessonsForApp.setNot_skipped_msg("同学，不可以连续跳过噢，先学一课吧。");
            return lessonsForApp;
        }

        public static LessonsForApp buildByCourses(Courses courses) {
            LessonsForApp result = new LessonsForApp();

            courses.foreach(course -> {
                LessonForApp lessonForApp = new LessonForApp();
                lessonForApp.setId(course.getCourseId());
                lessonForApp.setName(course.getCourseName());
                lessonForApp.setType(course.getCourseType().name());
                lessonForApp.setDifficulty(course.getCourseDifficulty().getIndex());
                lessonForApp.setCover(course.getCover());
                lessonForApp.setPrice(course.getPrice());
                lessonForApp.setLastModified(course.getPublicDate().getMillis());

                result.getCourses().add(lessonForApp);
            });

            return result;
        }
    }
}