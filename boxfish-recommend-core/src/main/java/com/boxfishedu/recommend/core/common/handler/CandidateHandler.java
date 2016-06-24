package com.boxfishedu.recommend.core.common.handler;

import com.boxfishedu.component.boxfish.protocal.domain.CourseDifficulties;
import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.component.boxfish.protocal.enums.LearningDifficulty;
import com.boxfishedu.component.boxfish.util.string.FormatUtil;
import com.boxfishedu.recommend.core.app.conf.RecommendProperties;
import com.boxfishedu.recommend.core.app.http.HttpAsyncTemplateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class CandidateHandler {

    private static Logger logger = LoggerFactory.getLogger(HistoryHandler.class);


    public Courses getCoursesByLearningDifficulty(LearningDifficulty learningDifficulty, Map<String, String> httpHeads) throws Exception {
        return requestCandidate(learningDifficulty.toCourseDifficultyList(), httpHeads);
    }


    @Autowired
    RecommendProperties recommendProperties;
    @Autowired
    HttpAsyncTemplateRequest httpAsyncTemplateRequest;


    private Courses requestCandidate(CourseDifficulties courseDifficultyList, Map<String, String> httpHeads) throws Exception {
        String response = httpAsyncTemplateRequest.post(recommendProperties.getServiceForCandidate(), null, null, httpHeads, courseDifficultyList.toJson());
        logger.debug("[boxfish-online-calculator] [requestCandidate] 请求可用课程. DATA=[{}]", response);
        return FormatUtil.fromJson(response, Courses.class);
    }
}
