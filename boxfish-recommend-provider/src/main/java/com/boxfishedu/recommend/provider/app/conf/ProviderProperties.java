package com.boxfishedu.recommend.provider.app.conf;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Component
@ConfigurationProperties(prefix = "boxfish.recommend")
public class ProviderProperties {

    @NotNull
    private List<String> illegalVersion;

}
