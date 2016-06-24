package com.boxfishedu.recommend.core.recommend.tradition;



import com.boxfishedu.component.boxfish.protocal.domain.Courses;
import com.boxfishedu.component.boxfish.protocal.enums.CourseDifficulty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TraditionTreeRoot {

    private Map<CourseDifficulty, TraditionTreeBranch> treeRoot = new ConcurrentHashMap<>();


    public TraditionTreeRoot() {
        treeRoot.put(CourseDifficulty.LEVEL_1, new TraditionTreeBranch());
        treeRoot.put(CourseDifficulty.LEVEL_2, new TraditionTreeBranch());
        treeRoot.put(CourseDifficulty.LEVEL_3, new TraditionTreeBranch());
        treeRoot.put(CourseDifficulty.LEVEL_4, new TraditionTreeBranch());
        treeRoot.put(CourseDifficulty.LEVEL_5, new TraditionTreeBranch());
    }

    public TraditionTreeRoot load(Courses courses) {
        courses.foreach(course -> treeRoot.get(course.getCourseDifficulty()).load(course));
        return this;
    }

    public TraditionTreeRoot loadResultBuffer() {
        treeRoot.forEach((difficulty, treeRoot) -> treeRoot.loadResultBuffer());
        return this;
    }

    public Courses takeFromResultBuffer(CourseDifficulty difficulty, Integer size) {
        return treeRoot.get(difficulty).getResultBuffer().takeBySize(size);
    }

    public int sizeOfResultBuffer(CourseDifficulty difficulty) {
        return treeRoot.get(difficulty).getResultBuffer().size();
    }

}