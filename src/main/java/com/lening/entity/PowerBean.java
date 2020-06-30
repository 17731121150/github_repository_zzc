package com.lening.entity;

/**
 * 创作时间：2020/4/8 18:58
 * 作者：张志超
 */
public class PowerBean {
    private Integer id;
    private Integer pid;
    private String pname;
    /**
     * 样式
     */
    private String icon;
    private String url;
    private String target;

    private boolean checked=false;
    private String isbutton;        //是否为按钮

    public String getIsbutton() {
        return isbutton;
    }

    public void setIsbutton(String isbutton) {
        this.isbutton = isbutton;
    }

    public boolean isChecked() {
        return checked;
    }
    /**
     * boolean类型属性的set和is方法，不是get方法
     * @return
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "PowerBean{" +
                "id=" + id +
                ", pid=" + pid +
                ", pname='" + pname + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", target='" + target + '\'' +
                ", checked=" + checked +
                ", isbutton='" + isbutton + '\'' +
                '}';
    }
}
