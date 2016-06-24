package com.boxfishedu.recommend.provider.candidate.domain;

import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data

@Document(collection = "course_new")
@TypeAlias("cn.boxfishedu.cloud.tagservice.entity.nosql.Tag")
public class PrimaryCourseEntity {

    private String bookSectionId;
    private List<String> courseType;
    private List<String> difficulty;
    private Boolean recommend;


    public Course toCourse() {
        Course course = new Course();
        course.setCourseId(bookSectionId);

        // 将老课程的难度级别向下取整数,如果是零级则调整到一级
        Integer diff = Double.valueOf(difficulty.get(0)).intValue();
        if (diff == 0) diff = 1;
        course.setCourseDifficulty(CourseDifficulty.parse(diff.toString()));

        return course;
    }

}
