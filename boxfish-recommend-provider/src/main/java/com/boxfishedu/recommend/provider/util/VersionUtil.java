package com.boxfishedu.recommend.provider.util;


import com.boxfishedu.recommend.provider.app.conf.ProviderProperties;
import com.boxfishedu.recommend.provider.app.exception.IllegalVersionException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Component
public class VersionUtil {

    @Autowired
    ProviderProperties recommendProperties;


    public void checkVersion(HttpHeaders headers) throws IllegalVersionException {

        String headTag = headers.getFirst("x-be-product");
        if (headTag != null) {
            String target = StringUtils.substringAfterLast(headTag, ":");

            if (recommendProperties.getIllegalVersion().contains(target)) {
                throw new IllegalVersionException();
            }
        }
    }

}

