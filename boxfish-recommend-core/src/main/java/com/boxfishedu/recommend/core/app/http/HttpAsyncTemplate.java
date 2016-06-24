package com.boxfishedu.recommend.core.app.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;


@Configuration
public class HttpAsyncTemplate {

    @Bean(name = "HttpAsyncTemplate")
    public AsyncRestTemplate getHttpAsyncTemplate() throws Exception {

        HttpComponentsAsyncClientHttpRequestFactory factory = new HttpComponentsAsyncClientHttpRequestFactory();
        factory.setConnectTimeout(30_000);
        factory.setReadTimeout(30_000);

        return new AsyncRestTemplate(factory);
    }

}
