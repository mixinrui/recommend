package com.boxfishedu.recommend.provider.history.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface HistoryRepository extends JpaRepository<HistoryEntity, String> {

    Collection<HistoryEntity> findByUserId(Long userId);
}
