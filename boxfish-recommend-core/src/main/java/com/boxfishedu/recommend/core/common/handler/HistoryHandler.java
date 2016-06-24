package com.boxfishedu.recommend.core.common.handler;


import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.component.boxfish.util.string.FormatUtil;
import com.boxfishedu.recommend.core.app.conf.RecommendProperties;
import com.boxfishedu.recommend.core.app.http.HttpAsyncTemplateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;


@Component
public class HistoryHandler {

    public Courses getLearnedCourses(Long userId) throws Exception {
        return requestHistory(userId);
    }


    @Autowired
    RecommendProperties recommendProperties;
    @Autowired
    HttpAsyncTemplateRequest httpAsyncTemplateRequest;


    private Courses requestHistory(Long userId) throws Exception {
        Map<String, String> parameter = Collections.singletonMap("user_id", userId.toString());
        String response = httpAsyncTemplateRequest.post(recommendProperties.getServiceForHistory(), parameter, null, null, null);
        return FormatUtil.fromJson(response, Courses.class);
    }
}
