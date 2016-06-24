package com.boxfishedu.recommend.core.buffer.service;

import com.boxfishedu.component.boxfish.protocal.enums.Flag;
import com.boxfishedu.recommend.core.common.domain.UserFlagSkipEntity;
import com.boxfishedu.recommend.core.common.domain.UserFlagSkipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserFlagSkipService {

    @Autowired
    UserFlagSkipRepository userFlagSkipRepository;


    public Boolean checkFlagTrue(Long userId) {
        Optional<UserFlagSkipEntity> userFlagSkipEntity = Optional.ofNullable(userFlagSkipRepository.findOne(userId));
        return userFlagSkipEntity.isPresent() && userFlagSkipEntity.get().getIsSkip().equals(Flag.TRUE);
    }

    public void setFlagFalse(Long userId) {
        UserFlagSkipEntity userFlagSkipEntity = new UserFlagSkipEntity();
        userFlagSkipEntity.setUserId(userId);
        userFlagSkipEntity.setIsSkip(Flag.FALSE);

        userFlagSkipRepository.save(userFlagSkipEntity);
    }

    public void setFlagTrue(Long userId) {
        UserFlagSkipEntity userFlagSkipEntity = new UserFlagSkipEntity();
        userFlagSkipEntity.setUserId(userId);
        userFlagSkipEntity.setIsSkip(Flag.TRUE);

        userFlagSkipRepository.save(userFlagSkipEntity);
    }

}
