package com.boxfishedu.recommend.core.recommend.online;




import com.boxfishedu.component.boxfish.protocal.enums.CourseType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangshichao on 16/5/20.
 */
public enum OnlineRecommendRuleConfig {

    rule;
    Map<Integer,List<CourseType>> map = new ConcurrentHashMap<>();

    /**
     * 初始化课程类型推荐规则
     */
    private OnlineRecommendRuleConfig(){

        map.put(1,new ArrayList<>(Arrays.asList(CourseType.READING, CourseType.CONVERSATION)));
        map.put(2,new ArrayList<>(Arrays.asList(CourseType.CONVERSATION, CourseType.READING)));
        map.put(3,new ArrayList<>(Arrays.asList(CourseType.FUNCTION, CourseType.CONVERSATION, CourseType.READING)));
        map.put(4,new ArrayList<>(Arrays.asList(CourseType.FUNCTION, CourseType.CONVERSATION, CourseType.READING)));
        map.put(5,new ArrayList<>(Arrays.asList(CourseType.PHONICS,CourseType.EXAMINATION)));
        map.put(6,new ArrayList<>(Arrays.asList(CourseType.FUNCTION, CourseType.CONVERSATION, CourseType.READING)));
        map.put(7,new ArrayList<>(Arrays.asList(CourseType.FUNCTION, CourseType.CONVERSATION, CourseType.READING)));
        map.put(8,new ArrayList<>(Arrays.asList(CourseType.TALK)));
    }

    /**
     * 根据上得是第几次课确定课程类型
     * @param index
     * @return
     */
    public List<CourseType> getTypesByIndex(Integer index){
        return map.get(index);
    }

}
