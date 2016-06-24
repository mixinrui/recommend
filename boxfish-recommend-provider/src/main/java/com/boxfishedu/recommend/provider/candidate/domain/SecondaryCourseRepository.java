package com.boxfishedu.recommend.provider.candidate.domain;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface SecondaryCourseRepository extends MongoRepository<SecondaryCourseEntity, String> {

    SecondaryCourseEntity findByBookSectionId(String bookSectionId);

    Collection<SecondaryCourseEntity> findByDifficultyInAndRecommend(List<String> difficulty, Boolean recommend);

    // 苹果审核专用,仅提供 GRAMMAR
    Collection<SecondaryCourseEntity> findByCourseTypeIgnoreCaseAndRecommend(String courseType, Boolean recommend);

}
