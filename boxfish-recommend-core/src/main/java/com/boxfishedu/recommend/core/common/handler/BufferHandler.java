package com.boxfishedu.recommend.core.common.handler;


import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.recommend.core.common.domain.UserBufferEntity;
import com.boxfishedu.recommend.core.common.domain.UserBufferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class BufferHandler {

    @Autowired
    UserBufferRepository bufferRepository;


    public Courses getCourses(Long userId) throws Exception {
        Collection<UserBufferEntity> bufferEntities = bufferRepository.findByUserId(userId);

        Courses result = new Courses();
        bufferEntities.forEach(bufferEntity -> {
            Course course = new Course();
            course.setCourseId(bufferEntity.getCourseId());

            result.add(course);
        });

        return result;
    }
}
