package com.boxfishedu.bebase3.recommend.v3.recommend2.common.handler.preference;

import com.boxfishedu.component.boxfish.protocal.domain.Preference;
import com.boxfishedu.recommend.core.Application;
import com.boxfishedu.recommend.core.common.handler.PreferenceHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
public class PreferenceHandlerTest {

    @Autowired
    PreferenceHandler preferenceHandler;

    @Test
    public void testGetPreference() throws Exception {
        Preference preference = preferenceHandler.getPreference(7431L);
    }

}