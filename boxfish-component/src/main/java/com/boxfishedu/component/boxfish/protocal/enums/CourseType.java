package com.boxfishedu.component.boxfish.protocal.enums;


public enum CourseType {

    WORDS,
    GRAMMAR,
    FUNCTION,
    READING,
    CONVERSATION,
    QUIZ,
    WRITING,
    MUSIC,
    LISTENING,
    TALK,
    EXAMINATION,
    PHONICS,
    TEXTBOOK_WORDS,
    TEXTBOOK_READING,
    TEXTBOOK_LISTENING,
    TEXTBOOK_QUIZ;


    public static CourseType parse(String type) {
        return CourseType.valueOf(type.toUpperCase());
    }

}
