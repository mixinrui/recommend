package com.boxfishedu.recommend.core.common.handler;

import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.util.string.FormatUtil;
import com.boxfishedu.recommend.core.app.conf.RecommendProperties;
import com.boxfishedu.recommend.core.app.http.HttpAsyncTemplateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;


@Component
public class CourseHandler {

    public CourseDifficulty getCourseDifficulty(String courseId) throws Exception {
        return requestCourseInfo(courseId).getCourseDifficulty();
    }


    @Autowired
    RecommendProperties recommendProperties;
    @Autowired
    HttpAsyncTemplateRequest httpAsyncTemplateRequest;


    private Course requestCourseInfo(String courseId) throws Exception {
        Map<String, String> parameter = Collections.singletonMap("course_id", courseId);
        String response = httpAsyncTemplateRequest.post(recommendProperties.getServiceForCourseInfo(), parameter, null, null, null);

        return FormatUtil.fromJson(response, Course.class);
    }
}
