package com.lening.mapper;

import com.lening.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 创作时间：2020/4/7 10:48
 * 作者：李增强
 */
public interface UserMapper {
    List<UserBean> findAll();

    List<PowerBean> getPowerList();

    /**
     * 一个可以不用写注解，两个及两个以上必须写
     */
    UserBean getUserById(@Param("id") Integer id);

    List<DeptBean> getDeptList();

    List getRoleListByDeptid(@Param("deptid") Integer deptid);

    void saveUserDeptRole(@Param("id") Integer id, @Param("deptid") Integer deptid, @Param("rid") Integer rid);


    List<UserBean> getLogin(@Param("username") String username);

    List<PowerBean> getPowerListById(@Param("id") Integer id);

    RsBean getRsSmoke(String cardno);

    RsBean getRsWine(String cardno);

    void saveSmoke(SmokeBean smokeBean);

    void saveWine(WineBean wineBean);

    /**
     * mapper接口中的方法不能重载
     */
    void insertPorcess(ProcessBean pb);
    GradeBean getGradeByGid(@Param("gid") Integer gid);
    void insertProcessPmx(PmxBean pmxBean);

    List<QjVo> getStuQjListBySid(@Param("sid") Integer sid);


    Integer getUserIdByPid(@Param("pid") Integer pid);//这是流程id
    Integer getUserIdByPidMaxShunxu(@Param("pid") Integer pid);//审核通过的
    Integer getUserIdByPidNopass(@Param("pid") Integer pid);//审核没通过的
    QjVo getUnameAndRnameById(@Param("id") Integer id);

    List<Integer> getPidsByUserid(@Param("userid") Integer userid);
    QjVo getProcessById(@Param("id") Integer id);
    QjVo getStuInfoBySid(@Param("id") Integer id);


    void updateProcessStatus(@Param("pid") Integer pid,@Param("shstatus") Integer shstatus);
    void updatePmxStatus(@Param("pid") Integer pid, @Param("userid") Integer userid,@Param("shstatus")Integer shstatus);
    Integer getMaxPshunxu(@Param("pid") Integer pid);
    Integer getQjMxInfo(@Param("pid") Integer pid, @Param("userid") Integer userid);
    void updatePmxShunxu(@Param("pid") Integer pid, @Param("pshunxu") Integer pshunxu);
}
