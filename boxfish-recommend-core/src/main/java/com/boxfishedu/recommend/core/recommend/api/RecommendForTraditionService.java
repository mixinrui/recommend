package com.boxfishedu.recommend.core.recommend.api;


import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.recommend.core.app.exception.DifficultyUpgradeException;
import com.boxfishedu.recommend.core.common.upgrade.BeStateMachinePersist;
import com.boxfishedu.recommend.core.recommend.tradition.CalculatorForTradition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class RecommendForTraditionService {

    @Autowired
    BeStateMachinePersist stateMachinePersist;
    @Autowired
    CalculatorForTradition recommend;


    public Courses recommendCourses(Long userId, Map<String, String> httpHeads) throws Exception {
        try {
            return recommend.run(userId, httpHeads);

        } catch (DifficultyUpgradeException upgradeException) {
            stateMachinePersist.upgrade(userId);
            return recommendCourses(userId, httpHeads);
        }
    }

}