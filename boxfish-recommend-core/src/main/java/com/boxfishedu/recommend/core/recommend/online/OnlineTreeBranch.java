package com.boxfishedu.recommend.core.recommend.online;




import com.boxfishedu.component.boxfish.protocal.domain.Course;

import java.util.Optional;
import java.util.TreeSet;


public class OnlineTreeBranch {

    private TreeSet<Course> branch ;


    public OnlineTreeBranch(TreeSet<Course> branch){
        this.branch = branch;
    }

    public void put(Course course) {

        branch.add(course);
    }

    public Optional<Course> pop() {
        return Optional.ofNullable(branch.pollFirst());
    }

    public int size() {
        return branch.size();
    }

    public TreeSet<Course> getBranch() {
        return branch;
    }
}
