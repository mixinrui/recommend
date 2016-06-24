package com.boxfishedu.recommend.core.buffer.web;

import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.recommend.core.buffer.service.UserBufferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/recommend/core")
public class RecommendForOnlineController {

    private static Logger logger = LoggerFactory.getLogger(RecommendForOnlineController.class);

    @Autowired
    UserBufferService userBufferService;


    @RequestMapping(value = "/online/user_id/{user_id}/index/{index}", method = RequestMethod.POST)
    public Object onlineRecommend(@PathVariable(value = "user_id") Long userId,
                                  @PathVariable(value = "index") Integer index) throws Exception {

        logger.info("[boxfish-recommend-core] [onlineRecommend] 在线排课请求. ID=[{}] DATA=[{}]", userId, index);

        Course course = userBufferService.recommendCoursesForOnline(userId, index);

        logger.info("[boxfish-recommend-core] [onlineRecommend] 在线排课应答. ID=[{}] DATA=[{}]", userId, course);

        return course.toJson();
    }

}
