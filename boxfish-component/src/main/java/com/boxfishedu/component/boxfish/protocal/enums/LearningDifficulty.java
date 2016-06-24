package com.boxfishedu.component.boxfish.protocal.enums;


import com.boxfishedu.component.boxfish.protocal.domain.CourseDifficulties;
import com.google.common.collect.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public enum LearningDifficulty {

    LEVEL_1(Lists.newArrayList(CourseDifficulty.LEVEL_1), 100),
    LEVEL_1P(Lists.newArrayList(CourseDifficulty.LEVEL_1, CourseDifficulty.LEVEL_2), 50),
    LEVEL_2(Lists.newArrayList(CourseDifficulty.LEVEL_2), 100),
    LEVEL_2P(Lists.newArrayList(CourseDifficulty.LEVEL_2, CourseDifficulty.LEVEL_3), 50),
    LEVEL_3(Lists.newArrayList(CourseDifficulty.LEVEL_3), 100),
    LEVEL_3P(Lists.newArrayList(CourseDifficulty.LEVEL_3, CourseDifficulty.LEVEL_4), 50),
    LEVEL_4(Lists.newArrayList(CourseDifficulty.LEVEL_4), 100),
    LEVEL_4P(Lists.newArrayList(CourseDifficulty.LEVEL_4, CourseDifficulty.LEVEL_5), 50),
    LEVEL_5(Lists.newArrayList(CourseDifficulty.LEVEL_5), Integer.MAX_VALUE);


    private List<CourseDifficulty> courseDifficultyList;
    private Integer upgradeThreshold;


    LearningDifficulty(List<CourseDifficulty> courseDifficultyList, Integer upgradeThreshold) {
        this.courseDifficultyList = courseDifficultyList;
        this.upgradeThreshold = upgradeThreshold;
    }

    public static List<CourseDifficulty> getCourseDifficultyList(LearningDifficulty learningDifficulty) {
        return learningDifficulty.courseDifficultyList;
    }

    public static Integer getCourseDifficultyListSize(LearningDifficulty learningDifficulty) {
        return learningDifficulty.courseDifficultyList.size();
    }

    public static Integer getUpgradeThreshold(LearningDifficulty learningDifficulty) {
        return learningDifficulty.upgradeThreshold;
    }

    public static Integer getUpgradeThresholdPerCourseDifficulty(LearningDifficulty learningDifficulty) {
        return Math.floorDiv(learningDifficulty.upgradeThreshold, learningDifficulty.courseDifficultyList.size());
    }

    public static LearningDifficulty parse(String index) {
        return LearningDifficulty.valueOf("LEVEL_" + index);
    }

    public static Set<String> toSet() {
        Set<String> set = new HashSet<>();
        for (LearningDifficulty learningDifficulty : LearningDifficulty.values()) {
            set.add(learningDifficulty.name());
        }
        return set;
    }

    public CourseDifficulties toCourseDifficultyList() {
        CourseDifficulties courseDifficultyList = new CourseDifficulties();
        courseDifficultyList.getDifficultyList().addAll(this.courseDifficultyList);
        return courseDifficultyList;
    }

}
