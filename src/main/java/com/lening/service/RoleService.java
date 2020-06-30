package com.lening.service;

import com.lening.entity.PowerBean;
import com.lening.entity.RoleBean;

import java.util.List;

public interface RoleService {
    List<RoleBean> getRoleList();

    List<PowerBean> getRolePower(Integer rid);

    void insertRolePower(Integer rid, String ids);
}
