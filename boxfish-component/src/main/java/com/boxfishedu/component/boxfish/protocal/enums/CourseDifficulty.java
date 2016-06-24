package com.boxfishedu.component.boxfish.protocal.enums;


public enum CourseDifficulty {

    LEVEL_1("1"),
    LEVEL_2("2"),
    LEVEL_3("3"),
    LEVEL_4("4"),
    LEVEL_5("5");


    private String index;


    CourseDifficulty(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public static CourseDifficulty parse(String index) {
        return CourseDifficulty.valueOf("LEVEL_" + index);
    }

}
