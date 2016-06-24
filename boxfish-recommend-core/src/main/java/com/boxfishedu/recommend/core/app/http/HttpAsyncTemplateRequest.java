package com.boxfishedu.recommend.core.app.http;


import com.boxfishedu.component.boxfish.util.string.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.AsyncRestTemplate;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.Future;


@Component
public class HttpAsyncTemplateRequest {

    @Autowired
    @Qualifier("HttpAsyncTemplate")
    AsyncRestTemplate asyncRestTemplate;
    @Autowired
    HttpHeaderBuilder httpHeaderBuilder;
    @Autowired
    HttpParametersBuilder httpParametersBuilder;


    public String get(@NotNull String url, Map<String, String> restParameters, Map<String, String> classicParameters, Map<String, String> headers) throws Exception {
        HttpEntity requestEntity = new HttpEntity(httpHeaderBuilder.getHeaderWithJson(headers));
        String innerURL = httpParametersBuilder.getUrlWithClassicParameters(url, classicParameters);
        Map<String, String> innerRestParameters = httpParametersBuilder.getRestParameters(restParameters);

        try {
            Future<ResponseEntity<String>> futureEntity = asyncRestTemplate.exchange(innerURL, HttpMethod.GET, requestEntity, String.class, innerRestParameters);

            if (futureEntity.get().getStatusCode().is2xxSuccessful()) {
                return futureEntity.get().getBody();
            } else {
                throw new IllegalStateException(String.format("GET请求异常. URL = %s, RestParameters = %s, ClassicParameters = %s, Headers = %s",
                        url,
                        FormatUtil.toJsonNoException(restParameters),
                        FormatUtil.toJsonNoException(classicParameters),
                        FormatUtil.toJsonNoException(headers)
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("GET请求异常. URL = %s, RestParameters = %s, ClassicParameters = %s, Headers = %s",
                    url,
                    FormatUtil.toJsonNoException(restParameters),
                    FormatUtil.toJsonNoException(classicParameters),
                    FormatUtil.toJsonNoException(headers)
            ));
        }
    }

    public String get(@NotNull String url) throws Exception {
        return get(url, null, null, null);
    }

    @SuppressWarnings("unchecked")
    public String post(@NotNull String url, Map<String, String> restParameters, Map<String, String> classicParameters, Map<String, String> headers, String body) throws Exception {
        HttpEntity requestEntity = new HttpEntity(body, httpHeaderBuilder.getHeaderWithJson(headers));
        String innerURL = httpParametersBuilder.getUrlWithClassicParameters(url, classicParameters);
        Map<String, String> innerRestParameters = httpParametersBuilder.getRestParameters(restParameters);

        try {
            Future<ResponseEntity<String>> futureEntity = asyncRestTemplate.exchange(innerURL, HttpMethod.POST, requestEntity, String.class, innerRestParameters);

            if (futureEntity.get().getStatusCode().is2xxSuccessful()) {
                return futureEntity.get().getBody();
            } else {
                throw new IllegalStateException(String.format("POST请求异常. URL = %s, RestParameters = %s, ClassicParameters = %s, Headers = %s, Body= %s",
                        url,
                        FormatUtil.toJsonNoException(restParameters),
                        FormatUtil.toJsonNoException(classicParameters),
                        FormatUtil.toJsonNoException(headers),
                        FormatUtil.toJsonNoException(body)
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("POST请求异常. URL = %s, RestParameters = %s, ClassicParameters = %s, Headers = %s",
                    url,
                    FormatUtil.toJsonNoException(restParameters),
                    FormatUtil.toJsonNoException(classicParameters),
                    FormatUtil.toJsonNoException(headers)
            ));
        }
    }

    public String post(@NotNull String url, String body) throws Exception {
        return post(url, null, null, null, body);
    }

}
