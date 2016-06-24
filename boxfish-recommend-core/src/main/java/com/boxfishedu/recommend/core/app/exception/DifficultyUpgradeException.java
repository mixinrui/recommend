package com.boxfishedu.recommend.core.app.exception;


/**
 * 学习难度升级异常, 用于触发难度升级操作。
 */
public class DifficultyUpgradeException extends Exception {

    public DifficultyUpgradeException() {
    }

    public DifficultyUpgradeException(String message) {
        super(message);
    }
}
