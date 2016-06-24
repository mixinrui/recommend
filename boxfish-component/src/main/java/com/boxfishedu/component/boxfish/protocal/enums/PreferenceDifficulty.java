package com.boxfishedu.component.boxfish.protocal.enums;


public enum PreferenceDifficulty {

    LEVEL_1(1),
    LEVEL_2(2),
    LEVEL_3(3),
    LEVEL_4(4);


    private Integer index;

    PreferenceDifficulty(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }
}
