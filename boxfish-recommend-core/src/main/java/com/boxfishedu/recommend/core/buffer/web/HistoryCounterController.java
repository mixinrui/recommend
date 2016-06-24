package com.boxfishedu.recommend.core.buffer.web;

import com.boxfishedu.recommend.core.common.handler.LearningCounterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/recommend/core")
public class HistoryCounterController {

    private static Logger logger = LoggerFactory.getLogger(PreferenceController.class);

    @Autowired
    LearningCounterHandler learningCounterHandler;


    @RequestMapping(value = "/counter/user_id/{user_id}/lesson_id/{lesson_id}", method = RequestMethod.POST)
    public Object resetCounter(@PathVariable(value = "user_id") Long userId,
                               @PathVariable(value = "lesson_id") String courseId) throws Exception {

        logger.info("[boxfish-recommend-core] [resetCounter] 课程计数请求. ID=[{}] DATA=[{}]", userId, courseId);

        learningCounterHandler.increaseCounter(userId, courseId);
        return ResponseEntity.ok().build();
    }
}
