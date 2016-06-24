package com.boxfishedu.recommend.provider.history.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;


public interface CourseMappingRepository extends JpaRepository<CourseMappingEntity, Long> {

    Collection<CourseMappingEntity> findByOldCourseIdIn(List<String> oldCourseIdList);
}
