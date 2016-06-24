package com.boxfishedu.recommend.provider.candidate.domain;


import org.springframework.data.mongodb.repository.MongoRepository;


public interface PrimaryCourseRepository extends MongoRepository<PrimaryCourseEntity, String> {

    PrimaryCourseEntity findByBookSectionId(String bookSectionId);

}
