package com.boxfishedu.recommend.core.common.handler;


import com.boxfishedu.component.boxfish.protocal.domain.Preference;
import com.boxfishedu.component.boxfish.protocal.enums.PreferenceDifficulty;
import com.boxfishedu.component.boxfish.util.string.FormatUtil;
import com.boxfishedu.recommend.core.app.conf.RecommendProperties;
import com.boxfishedu.recommend.core.app.http.HttpAsyncTemplateRequest;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;


@Component
public class PreferenceHandler  {

    private static Logger logger = LoggerFactory.getLogger(PreferenceHandler.class);

    public Preference getPreference(Long userId) throws Exception {
        PreferenceRequest preferenceRequest = new PreferenceRequest();
        preferenceRequest.setUserId(userId);

        return requestPreference(preferenceRequest).toPreference();
    }


    @Autowired
    RecommendProperties recommendProperties;
    @Autowired
    HttpAsyncTemplateRequest httpAsyncTemplateRequest;


    private PreferenceResponse requestPreference(PreferenceRequest preferenceRequest) throws Exception {
        Map<String, String> parameter = Collections.singletonMap("user_id", preferenceRequest.getUserId().toString());
        String response = httpAsyncTemplateRequest.get(recommendProperties.getServiceForPreference(), parameter, null, null);

        PreferenceResponse preferenceResponse = FormatUtil.fromJson(response, PreferenceResponse.class);
        logger.debug("[boxfish-online-calculator] [requestPreference] 请求用户偏好. ID=[{}] DATA=[{}]", preferenceResponse.getUserId(), preferenceResponse);
        return preferenceResponse;
    }

    @Data
    private static class PreferenceRequest {
        private Long userId;
    }

    @Data
    private static class PreferenceResponse {
        private Long userId;

        private String publication;
        private String fitGrade;
        private String book;
        private String difficulty;

        private String message;
        private String status;


        public Preference toPreference() {
            Preference preference = new Preference();
            preference.setUserId(userId);
            preference.setPreferenceDifficulty(PreferenceDifficulty.valueOf("LEVEL_" + difficulty));

            return preference;
        }
    }

}
