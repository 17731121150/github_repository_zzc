package com.lening.service;

import com.lening.entity.DeptBean;
import com.lening.entity.RoleBean;

import java.util.List;

public interface DeptService {
    List<DeptBean> getDeltList();

    DeptBean getDeptByDeptid(Integer deptid);

    List<RoleBean> getRoleList();

    List<Integer> RidsByDeptid(Integer deptid);

    void saveDeptRole(Integer deptid, Integer[] rids);
}
