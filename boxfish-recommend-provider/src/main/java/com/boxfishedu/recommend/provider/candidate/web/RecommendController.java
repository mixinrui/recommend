package com.boxfishedu.recommend.provider.candidate.web;

import com.boxfishedu.component.boxfish.protocal.domain.CourseDifficulties;
import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.recommend.provider.app.exception.IllegalVersionException;
import com.boxfishedu.recommend.provider.candidate.service.RecommendService;
import com.boxfishedu.recommend.provider.util.VersionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/recommend/provider")
public class RecommendController {

    private static Logger logger = LoggerFactory.getLogger(RecommendService.class);

    @Autowired
    RecommendService candidateService;
    @Autowired
    VersionUtil versionUtil;


    @RequestMapping(value = "/lessons/candidate", method = RequestMethod.POST)
    public Object getCourses(
            @RequestBody CourseDifficulties courseDifficulties,
            @RequestHeader HttpHeaders headers) throws Exception {

        Courses result;
        try {
            versionUtil.checkVersion(headers);
            result = candidateService.getCandidateCourses(courseDifficulties.getDifficultyList());
            logger.debug("[boxfish-recommend-provider] [getCourses] 提供推荐课程.");

        } catch (IllegalVersionException e) {
            // 苹果审核,提供默认课程
            result = candidateService.getDefaultCourses();
            logger.debug("[boxfish-recommend-provider] [getCourses] 提供默认课程.");
        }

        return result.toJson();
    }

}
