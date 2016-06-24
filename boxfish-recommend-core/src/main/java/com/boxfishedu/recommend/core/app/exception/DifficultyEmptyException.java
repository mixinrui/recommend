package com.boxfishedu.recommend.core.app.exception;


/**
 * 当前学习难度没有课程异常
 */
public class DifficultyEmptyException extends Exception {

    public DifficultyEmptyException() {
    }

    public DifficultyEmptyException(String message) {
        super(message);
    }
}
