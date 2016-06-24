package com.boxfishedu.recommend.core.common.domain;

import com.boxfishedu.component.boxfish.protocal.enums.RecommendChannel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Transactional(readOnly = true)
public interface UserBufferRepository extends CrudRepository<UserBufferEntity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UserBufferEntity b WHERE b.userId = ?1 AND b.courseId = ?2")
    void deleteByUserIdAndCourseId(Long userId, String courseId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserBufferEntity b WHERE b.userId = ?1")
    void deleteByUserId(Long userId);

    Collection<UserBufferEntity> findByUserIdAndRecommendChannelOrderByIdAsc(Long userId, RecommendChannel recommendChannel);

    Collection<UserBufferEntity> findByUserId(Long userId);
}