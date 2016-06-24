package com.boxfishedu.recommend.core.common.domain;

import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.protocal.enums.CourseType;
import com.boxfishedu.component.boxfish.protocal.enums.RecommendChannel;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Data
@Entity
@Table(
        schema = "service",
        name = "recommend_buffer",
        indexes = {
                @Index(name = "index_user_id", columnList = "userId"),
                @Index(name = "index_recommend_channel", columnList = "userId,recommendChannel")}
)
public class UserBufferEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;

    private String courseId;
    private String courseName;
    private String cover;
    private Integer price;

    @Enumerated(value = EnumType.STRING)
    private CourseType courseType;
    @Enumerated(value = EnumType.STRING)
    private CourseDifficulty courseDifficulty;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime publicDate;

    @Enumerated(value = EnumType.STRING)
    private RecommendChannel recommendChannel;


    public Course toCourse() {
        Course result = new Course();
        result.setCourseId(courseId);
        result.setCourseName(courseName);
        result.setCourseType(courseType);
        result.setCourseDifficulty(courseDifficulty);
        result.setCover(cover);
        result.setPrice(price);
        result.setPublicDate(publicDate);

        return result;
    }

    public void fromCourse(Course course){
        this.setCourseId(course.getCourseId());
        this.setCourseName(course.getCourseName());
        this.setCover(course.getCover());
        this.setPrice(course.getPrice());
        this.setCourseType(course.getCourseType());
        this.setCourseDifficulty(course.getCourseDifficulty());
        this.setPublicDate(course.getPublicDate());
    }
}
