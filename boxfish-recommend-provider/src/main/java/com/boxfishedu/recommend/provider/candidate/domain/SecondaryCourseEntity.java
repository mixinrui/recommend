package com.boxfishedu.recommend.provider.candidate.domain;

import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;
import com.boxfishedu.component.boxfish.protocal.enums.CourseType;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data

@Document(collection = "new_version_tag")
@TypeAlias("cn.boxfishedu.cloud.tagservice.entity.nosql.Tag")
public class SecondaryCourseEntity {

    private String bookSectionId;
    private List<String> courseType;
    private List<String> difficulty;
    private MongoCourseBundle bundle;
    private Boolean recommend;

    private String courseName;
    private String cover;
    private Integer price;
    private Long publicDate;


    @Data
    public class MongoCourseBundle {
        private String bundleName;
        private String bundleId;
        private Integer bundleOrder;
    }

    public Course toCourse() {
        Course course = new Course();
        course.setCourseId(bookSectionId);
        course.setCourseName(courseName);
        course.setCover(cover);
        course.setPrice(price);
        course.setPublicDate(new DateTime(publicDate));
        course.setCourseDifficulty(CourseDifficulty.parse(difficulty.get(0)));
        course.setCourseType(CourseType.parse(courseType.get(0)));

        if (bundle != null) {
            course.setBundleId(bundle.getBundleId());
            course.setBundleOrder(bundle.getBundleOrder());
        }
        return course;
    }

}
