package com.boxfishedu.recommend.core.app.http;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class HttpHeaderBuilder {

    public HttpHeaders getHeaderWithJson(Map<String, String> customHeaders) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Accept-Charset", "UTF-8");
        requestHeaders.set("Content-Type", "application/json");

        // Set custom headers
        if (customHeaders != null) customHeaders.forEach(requestHeaders::set);
        return requestHeaders;
    }

    public HttpHeaders getHeaderWithJsonAndAuthorization(Map<String, String> customHeaders, String username, String password) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Accept-Charset", "UTF-8");
        requestHeaders.set("Content-Type", "application/json");

        String input = username + ":" + password;
        String encoding = Base64.encodeBase64String(input.getBytes());
        requestHeaders.set("Authorization", "Basic " + encoding);

        // Set custom headers
        if (customHeaders != null) customHeaders.forEach(requestHeaders::set);
        return requestHeaders;
    }

    public HttpHeaders getHeaderWithXml(Map<String, String> customHeaders) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Accept-Charset", "UTF-8");
        requestHeaders.set("Content-Type", "application/xml");

        // Set custom headers
        if (customHeaders != null) customHeaders.forEach(requestHeaders::set);
        return requestHeaders;
    }
}
