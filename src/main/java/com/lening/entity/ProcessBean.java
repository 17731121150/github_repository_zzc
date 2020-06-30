package com.lening.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ProcessBean {
    private  Integer id;
    private Integer sid;    //学生id
    private Double qjtime;  //学生的请假天数


    @DateTimeFormat(pattern = "yyyy-MM-dd HH")
    private Date stime;     //请假开始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH")
    private Date etime;     //请假结束时间
    private String qjcause; //请假原因
    private Integer qjstatus;//请假的状态

    private  Date qjdate = new Date();

    public Date getQjdate() {
        return qjdate;
    }
    public void setQjdate(Date qjdate) {
        this.qjdate = qjdate;
    }

    public Integer getQjstatus() {
        return qjstatus;
    }

    public void setQjstatus(Integer qjstatus) {
        this.qjstatus = qjstatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Double getQjtime() {
        return qjtime;
    }

    public void setQjtime(Double qjtime) {
        this.qjtime = qjtime;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    public String getQjcause() {
        return qjcause;
    }

    public void setQjcause(String qjcause) {
        this.qjcause = qjcause;
    }
}
