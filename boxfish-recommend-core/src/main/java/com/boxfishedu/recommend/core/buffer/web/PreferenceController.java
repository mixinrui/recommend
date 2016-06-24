package com.boxfishedu.recommend.core.buffer.web;

import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficulty;
import com.boxfishedu.recommend.core.buffer.service.UserBufferService;
import com.boxfishedu.recommend.core.buffer.service.UserFlagSkipService;
import com.boxfishedu.recommend.core.common.handler.LearningDifficultyHandler;
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
public class PreferenceController {

    private static Logger logger = LoggerFactory.getLogger(PreferenceController.class);

    @Autowired
    LearningDifficultyHandler learningDifficultyHandler;
    @Autowired
    UserBufferService userBufferService;
    @Autowired
    UserFlagSkipService userFlagSkipService;


    @RequestMapping(value = "/preference/user_id/{user_id}/level/{level}", method = RequestMethod.POST)
    public Object resetPreference(@PathVariable(value = "user_id") Long userId,
                                  @PathVariable(value = "level") String level) throws Exception {

        logger.info("[boxfish-recommend-core] [resetPreference] 重设偏好请求. ID=[{}] DATA=[{}]", userId, level);

        learningDifficultyHandler.saveLearningDifficultyByUserId(userId, LearningDifficulty.parse(level));
        userBufferService.clearCourse(userId);
        userFlagSkipService.setFlagFalse(userId);

        return ResponseEntity.ok().build();
    }

}
