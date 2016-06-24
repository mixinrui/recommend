package com.boxfishedu.recommend.provider.history.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;


@Data

@Entity
@Table(
        schema = "bebase",
        name = "user_has_lesson",
        indexes = {
                @Index(name = "index_user_id", columnList = "userId")
        }
)
public class HistoryEntity {

    @Id
    private String id;
    private Long userId;
    private String lesson;

}
