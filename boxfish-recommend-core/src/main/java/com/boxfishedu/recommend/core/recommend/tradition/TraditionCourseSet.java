package com.boxfishedu.recommend.core.recommend.tradition;


import com.boxfishedu.component.boxfish.protocal.domain.Course;
import lombok.Data;

import java.util.TreeSet;


@Data
public class TraditionCourseSet {

    private TreeSet<Course> courses = new TreeSet<>((Course c1, Course c2) -> (c1.getInnerOrder() - c2.getInnerOrder()));


    public void put(Course course) {
        courses.add(course);
    }

    public Course pop() {
        return courses.pollFirst();
    }

    public boolean checkEmpty(){
        return courses.isEmpty();
    }

}
