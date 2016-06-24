package com.boxfishedu.recommend.core.recommend.api;

import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.recommend.core.recommend.online.CalculatorForOnline;
import com.boxfishedu.recommend.core.common.upgrade.BeStateMachinePersist;
import com.boxfishedu.recommend.core.app.exception.DifficultyUpgradeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RecommendForOnlineService {

    @Autowired
    BeStateMachinePersist stateMachinePersist;
    @Autowired
    private CalculatorForOnline recommend;


    public Course recommendCourse(Long userId, Integer index) throws Exception {
        try {
            return recommend.getByIndex(userId, index);
        } catch (DifficultyUpgradeException upgradeException) {
            stateMachinePersist.upgrade(userId);
            return recommendCourse(userId, index);
        }
    }

}
