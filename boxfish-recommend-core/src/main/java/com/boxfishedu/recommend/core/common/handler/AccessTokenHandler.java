package com.boxfishedu.recommend.core.common.handler;

import com.boxfishedu.component.boxfish.util.string.FormatUtil;
import com.boxfishedu.recommend.core.app.conf.RecommendProperties;
import com.boxfishedu.recommend.core.app.http.HttpAsyncTemplateRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;


@Component
public class AccessTokenHandler {

    @Autowired
    RecommendProperties recommendProperties;
    @Autowired
    HttpAsyncTemplateRequest httpAsyncTemplateRequest;


    public Long getUserId(String accessToken) throws Exception {
        try {
            Map<String, String> parameter = Collections.singletonMap("access_token", accessToken);
            String response = httpAsyncTemplateRequest.get(recommendProperties.getServiceForToken(), null, parameter, null);

            UserInfo userInfo = FormatUtil.fromJson(response, UserInfo.class);
            return userInfo.id;

        } catch (Exception e) {
            throw new RuntimeException("用户鉴权异常.");
        }
    }


    @Data
    public static class UserInfo {
        Long id;
        String username;
        String access_token;
        Long score;
    }
}
