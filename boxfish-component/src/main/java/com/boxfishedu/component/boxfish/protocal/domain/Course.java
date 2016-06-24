package com.boxfishedu.component.boxfish.protocal.domain;

import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.protocal.enums.CourseType;
import com.boxfishedu.component.boxfish.util.bean.BeanToJson;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;


@Data
public class Course implements BeanToJson {

    private String courseId;
    private String courseName;
    private CourseType courseType;
    private CourseDifficulty courseDifficulty;
    private String cover;
    private Integer price;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime publicDate;

    private String bundleId;
    private Integer bundleOrder;
    private Integer innerOrder;


    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (null == other) return false;
        if (!(other instanceof Course)) return false;

        Course otherCourse = (Course) other;
        return this.courseId.equals(otherCourse.courseId);
    }

}
