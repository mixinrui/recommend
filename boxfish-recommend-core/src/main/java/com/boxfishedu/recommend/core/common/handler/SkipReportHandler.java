package com.boxfishedu.recommend.core.common.handler;

import com.boxfishedu.recommend.core.app.conf.RecommendProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
public class SkipReportHandler {

    private static Logger logger = LoggerFactory.getLogger(SkipReportHandler.class);

    @Autowired
    RecommendProperties recommendProperties;


    public void doReport(String token, String courseId) throws Exception {
        try {
            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
            form.add("course_id", courseId);
            form.add("is_skipped", true);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(recommendProperties.getServiceForReport() + "?access_token=" + token, form, null);

        } catch (Exception e) {
            logger.error("[boxfish-recommend-core] [doReport] 数据上报异常. EXCEPTION=[{}] ID=[{}] DATA=[{}]", e.toString(), token, courseId);
        }
    }

}
