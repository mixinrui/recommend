package com.boxfishedu.recommend.core.recommend.online;



import com.boxfishedu.component.boxfish.protocal.domain.Course;
import com.boxfishedu.component.boxfish.protocal.enums.CourseType;

import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangshichao on 16/5/23.
 */
public enum OnlineTreeRootConfig {

    rule;

    public static final Integer size = 8;

    /**
     * 初始化排序规则
     *
     * @param tree
     */
    private void initTreeConfig(Map<CourseType, OnlineTreeBranch> tree) {
        tree.put(CourseType.GRAMMAR, getBranchByOrderType(OnlineTreeBranchOrder.RANDOM));
        tree.put(CourseType.FUNCTION, getBranchByOrderType(OnlineTreeBranchOrder.RANDOM));
        tree.put(CourseType.READING, getBranchByOrderType(OnlineTreeBranchOrder.TIMEDESC));
        tree.put(CourseType.PHONICS, getBranchByOrderType(OnlineTreeBranchOrder.FIXEDSEQUENCE));
        tree.put(CourseType.TALK, getBranchByOrderType(OnlineTreeBranchOrder.FIXEDSEQUENCE));
        tree.put(CourseType.CONVERSATION, getBranchByOrderType(OnlineTreeBranchOrder.TIMEDESC));
        tree.put(CourseType.EXAMINATION, getBranchByOrderType(OnlineTreeBranchOrder.RANDOM));
    }

    /**
     * 初始化排序类型
     *
     * @param orderType
     * @return
     */
    private OnlineTreeBranch getBranchByOrderType(OnlineTreeBranchOrder orderType) {
        switch (orderType) {
            case RANDOM:
                return new OnlineTreeBranch(new TreeSet<>((Course c1, Course c2) -> ((int) (Math.random() * 10000) - (int) (Math.random() * 10000))));
            case TIMEDESC:
                return new OnlineTreeBranch(new TreeSet<>((Course c1, Course c2) -> (c2.getPublicDate().compareTo(c1.getPublicDate()))));
            case FIXEDSEQUENCE:
                return new OnlineTreeBranch(new TreeSet<>((Course c1, Course c2) -> (c1.getBundleOrder() - c2.getBundleOrder())));
            default:
                return new OnlineTreeBranch(new TreeSet<>());
        }
    }

    public Map<CourseType, OnlineTreeBranch> getTree() {
        Map<CourseType, OnlineTreeBranch> tree = new ConcurrentHashMap<>();
        initTreeConfig(tree);
        return tree;
    }

}
