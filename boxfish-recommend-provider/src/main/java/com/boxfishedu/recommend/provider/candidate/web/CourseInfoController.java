package com.boxfishedu.recommend.provider.candidate.web;

import com.boxfishedu.recommend.provider.candidate.service.CourseInfoService;
import com.boxfishedu.recommend.provider.candidate.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/recommend/provider")
public class CourseInfoController {

    private static Logger logger = LoggerFactory.getLogger(RecommendService.class);

    @Autowired
    CourseInfoService courseInfoService;


    @RequestMapping(value = "/lesson/info/{course_id}", method = RequestMethod.POST)
    public Object getInfo(@PathVariable(value = "course_id") String courseId) throws Exception {

        logger.debug("[boxfish-recommend-provider] [getInfo] 获取课程信息请求. DATA=[{}]", courseId);

        String response = courseInfoService.getCourse(courseId).toJson();
        logger.debug("[boxfish-recommend-provider] [getInfo] 获取课程信息应答. DATA=[{}]", response);

        return response;
    }

}
