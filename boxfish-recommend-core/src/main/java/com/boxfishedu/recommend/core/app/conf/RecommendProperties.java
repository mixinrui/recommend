package com.boxfishedu.recommend.core.app.conf;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


@Data
@Component
@ConfigurationProperties(prefix = "boxfish.recommend")
public class RecommendProperties {

    @NotNull
    private String serviceForPreference;
    @NotNull
    private String serviceForCandidate;
    @NotNull
    private String serviceForHistory;
    @NotNull
    private String serviceForToken;
    @NotNull
    private String serviceForCourseInfo;
    @NotNull
    private String serviceForReport;

}
