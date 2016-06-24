package com.boxfishedu.recommend.core.app.http;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class HttpParametersBuilder {

    public Map<String, String> getRestParameters(Map<String, String> restParameters) {
        return (restParameters != null) ? restParameters : new HashMap<>();
    }

    public String getUrlWithClassicParameters(String url, Map<String, String> classicParameters) {
        StringBuilder urlWithParameters = new StringBuilder(url);

        if (classicParameters != null) {
            urlWithParameters.append("?");
            classicParameters.forEach((key, value) -> urlWithParameters.append(key).append("=").append(value).append("&"));
            urlWithParameters.deleteCharAt(urlWithParameters.length() - 1);
        }
        return urlWithParameters.toString();
    }
}
