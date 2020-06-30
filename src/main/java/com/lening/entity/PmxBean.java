package com.lening.entity;

import java.util.Date;

public class PmxBean {  //Mingxi
    private Integer id;
    private Integer pid;        //请假流程id
    private Integer userid;     //操作者id
    private Integer status;     //流程状态，默认 0
    private String pyijian;     //流程意见
    private Integer pstatus;    //这个是流程处理的状态码： 0没有没到我这里，  1表示该我处理了，2表示我已经处理过了
    private Integer pshunxu;    //流程的顺序
    private Date shdate = new Date();

    public Date getShdate() {
        return shdate;
    }

    public void setShdate(Date shdate) {
        this.shdate = shdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPyijian() {
        return pyijian;
    }

    public void setPyijian(String pyijian) {
        this.pyijian = pyijian;
    }

    public Integer getPstatus() {
        return pstatus;
    }

    public void setPstatus(Integer pstatus) {
        this.pstatus = pstatus;
    }

    public Integer getPshunxu() {
        return pshunxu;
    }

    public void setPshunxu(Integer pshunxu) {
        this.pshunxu = pshunxu;
    }
}
