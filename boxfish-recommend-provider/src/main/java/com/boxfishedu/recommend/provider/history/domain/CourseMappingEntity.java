package com.boxfishedu.recommend.provider.history.domain;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(
        schema = "bebase",
        name = "course_mapping",
        indexes = {
                @Index(name = "index_old_course_id", columnList = "oldCourseId")
        }
)
public class CourseMappingEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String newCourseId;
    private String oldCourseId;

}
