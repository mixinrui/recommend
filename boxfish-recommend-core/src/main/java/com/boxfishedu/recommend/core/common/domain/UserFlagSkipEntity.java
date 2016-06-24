package com.boxfishedu.recommend.core.common.domain;

import com.boxfishedu.component.boxfish.protocal.enums.Flag;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "service", name = "recommend_flag_skip")
public class UserFlagSkipEntity {

    @Id
    Long userId;

    @Enumerated(value = EnumType.STRING)
    private Flag isSkip;

}
