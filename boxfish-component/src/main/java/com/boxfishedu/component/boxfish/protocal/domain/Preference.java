package com.boxfishedu.component.boxfish.protocal.domain;

import com.boxfishedu.component.boxfish.protocal.enums.PreferenceDifficulty;
import lombok.Data;


@Data
public class Preference {

    private Long userId;
    private PreferenceDifficulty preferenceDifficulty;

}
