package com.lening.entity;

/**
 * 创作时间：2020/4/9 13:54
 * 作者：张志超
 */
public class RoleBean {

    private Integer rid;            //角色id
    private String rname;           //角色名称

    private DeptBean deptBean = new DeptBean();

    public DeptBean getDeptBean() {
        return deptBean;
    }

    public void setDeptBean(DeptBean deptBean) {
        this.deptBean = deptBean;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

}
