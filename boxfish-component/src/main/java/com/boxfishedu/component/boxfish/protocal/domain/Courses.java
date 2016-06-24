package com.boxfishedu.component.boxfish.protocal.domain;


import com.boxfishedu.component.boxfish.util.bean.BeanToJson;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Data
public class Courses implements BeanToJson {

    @JsonProperty("single")
    private List<Course> courseList = new ArrayList<>();


    public void add(Course course) {
        courseList.add(course);
    }

    public void addAll(Courses courses) {
        courseList.addAll(courses.courseList);
    }

    public Integer size() {
        return courseList.size();
    }

    public boolean checkEmpty() {
        return courseList.isEmpty();
    }

    public void foreach(Consumer<Course> action) {
        courseList.forEach(action::accept);
    }

    public Courses filter(Courses courses) {
        courseList.removeAll(courses.courseList);
        return this;
    }

    public Courses takeBySize(Integer size) {
        Courses result = new Courses();
        result.setCourseList(courseList.subList(0, size));
        return result;
    }

}
