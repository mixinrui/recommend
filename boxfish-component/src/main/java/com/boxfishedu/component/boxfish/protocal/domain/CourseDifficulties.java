package com.boxfishedu.component.boxfish.protocal.domain;


import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.util.bean.BeanToJson;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Data
public class CourseDifficulties implements BeanToJson {

    @JsonProperty("difficulty")
    private List<CourseDifficulty> difficultyList = new ArrayList<>();


    public void add(CourseDifficulty courseDifficulty) {
        difficultyList.add(courseDifficulty);
    }

    public void foreach(Consumer<CourseDifficulty> action) {
        difficultyList.forEach(action::accept);
    }

}
