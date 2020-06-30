package com.lening.service.impl;

import com.lening.entity.*;
import com.lening.mapper.UserMapper;
import com.lening.service.UserService;
import com.lening.utils.JieXiXml;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<UserBean> findAll() {
        return userMapper.findAll();
    }

    @Override
    public List<PowerBean> getPowerList() {
        return userMapper.getPowerList();
    }

    @Override
    public UserBean getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public List<DeptBean> getDeptList() {
        return userMapper.getDeptList();
    }

    @Override
    public List<RoleBean> getRoleListByDeptid(UserBean userBean) {
        /**
         * 这一步为空必须判断，因为员工id使用页面传递过来，要是有人估计不传递，传递一个错误
         * 就查询不到 userBean，可能为空
         */
        if(userBean!=null){
            /**
             * 其实没有必要，因为只要userBean不为空，deptBean一定不为空 ，这是自己new的啊
             * 判断 deptBean.deptid不为空，这个表示该用户有没有分配部门
             */
            if(userBean.getDeptBean()!=null&&userBean.getDeptBean().getDeptid()!=null){
                List rlist = userMapper.getRoleListByDeptid(userBean.getDeptBean().getDeptid());
                return  rlist;
            }
        }
        return null;
    }

    @Override
    public List<RoleBean> getRoleListByDeptid2(Integer deptid) {
        return userMapper.getRoleListByDeptid(deptid);
    }

    @Override
    public void saveUserDeptRole(Integer id, Integer deptid, Integer rid) {
        /**
         * 这地方一定要细心，因为三个参数类型一样，容易混乱
         */
        userMapper.saveUserDeptRole(id,deptid,rid);
    }

    @Override
    public UserBean getLogin(UserBean userBean) {
        if(userBean!=null){
            if(userBean.getUsername()!=null&&!"".equals(userBean.getUsername())){
                /**
                 * 虽然你在实际生产不可以出现，一个用户名查询出来两条数据的可能性
                 * 但是测试有可能，所以，我们写的全面点，返回为list，判断list大小，是1就OK啦，在判断密码，否则登录失败
                 */
                List<UserBean> list = userMapper.getLogin(userBean.getUsername());
                if(list!=null&&list.size()==1){
                    UserBean ub = list.get(0);
                    if(ub.getPassword().equals(userBean.getPassword())){
                        return ub;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<PowerBean> getPowerListById(Integer id) {
        return userMapper.getPowerListById(id);
    }

    @Override
    public QueryVo jieXiStr1(String str1) {
        return JieXiXml.jieXiStr1(str1);
    }

    @Override
    public String jieXiStr2(String str2) {
        return JieXiXml.jieXiStr2(str2);
    }

    @Override
    public String getInfo(QueryVo vo) {
        /**
         * 我们发现香烟和酒水只有一个字段不一样。
         * 我我们可以把这两个试题合并，只适合我们这项目，实际中很少
         */
        RsBean rs=null;
        if("01".equals(vo.getCode())){
            /**
             * 要是code是01去查询香烟，到香烟的数据库里面去查询
             */
            rs = userMapper.getRsSmoke(vo.getCardno());

        }else if("02".equals(vo.getCode())){
            /**
             * 要是code是02去查询酒水，到酒水的数据库里面去查询
            */
            rs = userMapper.getRsWine(vo.getCardno());
        }

        String str = JieXiXml.pinRsStr(vo.getCode(),rs);
        if(str!=null){
            return str;
        }
        return null;
    }

    @Override
    public String saveData(QueryVo vo, String str2) {
        /**
         * 01是香烟
         */
        if("01".equals(vo.getCode())){

            /**
             * 这个str2目前没有解析的方法，解析里面的解析str2方法是解析查询时候的str2，就一个商品编号cardno
             */
            SmokeBean smokeBean = JieXiXml.jieXiStr2Smoke(str2);
            /**
             *解析出来进行判断保存
             */
            /**
             * 判断
             */
            if(smokeBean==null){
                return "<MEG><CODE>0</CODE><CONTENT>参数2解析失败</CONTENT></MEG>";
            }else{
                try {
                    /**
                     * 保存成功
                     */
                    userMapper.saveSmoke(smokeBean);

                    return "<MEG><CODE>1</CODE><CONTENT>保存成功了</CONTENT></MEG>";
                }catch (Exception e){
                    return "<MEG><CODE>0</CODE><CONTENT>数据保存失败</CONTENT></MEG>";
                }
            }

        }else if("02".equals(vo.getCode())){
            /**
             * 02是酒水
             */
            WineBean wineBean = JieXiXml.jieXiStr2Wine(str2);
            if(wineBean==null){
                return "<MEG><CODE>0</CODE><CONTENT>参数2解析失败</CONTENT></MEG>";
            }else{
                try {
                    /**
                     * 保存成功
                     */
                    userMapper.saveWine(wineBean);
                    return "<MEG><CODE>1</CODE><CONTENT>保存成功了</CONTENT></MEG>";
                }catch (Exception e){
                    return "<MEG><CODE>0</CODE><CONTENT>数据保存失败</CONTENT></MEG>";
                }
            }
        }
        return null;
    }



    @Override
    public void saveStuQj(Integer id, Double qjtime, Date stime, Date etime,String qjcause) {
        /**
         * 时间的判断 ，可以使用 两个日期进行做差，当然也可以使用  qjtime（需要在页面固定单位）
         * qjtime：固定他的但是是天
         */
        /**
         * 在进入请假流程之前，需要查询出学生的班级和班级的讲师以及辅导员，也可以把主任和院长查出来
         */


        /**
         *判断学生请假的长度，只是用来判断审核流程的站点的，学生请假进库和这个时间没有关系不管请多长时间，都需要进库
         */

        /**
         * 1、先把请假流程保存进去再说,需要把流程的id拿回来，因为我们保存流程明细的时候，需要流程的id。
         * 保存返回的id，只有是在实体的时候返回过，直接用方法返回，可以吗？可以试一下
         * 没有能把插入的id作为返回值返回回来的办法，那么就把数控封装成实体类
         */

        ProcessBean pb = new ProcessBean();
        pb.setSid(id);
        pb.setQjcause(qjcause);
        pb.setEtime(etime);
        pb.setStime(stime);
        pb.setQjtime(qjtime);
        /**
         * 流程状态，0表示 正在审核中，1表示审核通过，2表示审核未通过
         * 这个是整合流程的状态，整合请假流程现在的状态，供请假者来查询
         * 在明细表中的，记录的是每一个审批的状态
         */
        pb.setQjstatus(0);
        userMapper.insertPorcess(pb);
        System.out.println("pb========="+pb.getId());

        /**
         * 流程保存成功，接下来，根据时间保存流程明细，需要根据学生的sid去把学生班级信息查询出来，因为我们要查询学生班级的讲师和辅导员id
         * 查询到学生班级情况后，就有了讲师id和辅导员id，这样就能生成明细了，主任id和院长id这个是固定的，可以在班级里面加入，
         * 我们没有更大的单位了，还有教研室，还有学院（主任和id是固定的）
         */
        /**
         * 流程明细全部进去了，谁先来处理。都是0,但是因为讲师先审批，所以新增的时候，其他的都是0，讲师是1，表示正在审核，2表示已审核
         * 要是讲师或者辅导员，审核过了，不管是审核未通过还是审核通过，只要操作了，就把顺序码改成2，表示这个流程已经操作完了
         * 每个人登录上来，我们只需要查看id是自己的，然后顺序码是1的，那些流程明细，表示该我审核了。2表示我已经处理过了。0的表示还没有到我这里来。就OK啦
         */

        if(qjtime<=1){
            /**
             * 小于等于1的全部在这里了，给明细里面爆出一个讲师和辅导员的明细就OK啦
             */

        }else if(qjtime<=3){
            /**
             * 在这个里面没有必要在写  >1 && <=3，给明细表中，主任加一个明细
             */
        }else{
            /**
             * 给院长一个明细
             */
        }
    }

    @Override
    public void saveStuQj2(ProcessBean pb) {//学生提交请假信息
        pb.setQjstatus(0);      //学生只要提交了请假信息，就把该学生的请假状态设为默认的 0 ，就是正在审核中
        userMapper.insertPorcess(pb);   //然后先把信息保存到数据库中，把请假流程的 sid 返回回来到实体类中。
        UserBean ub = userMapper.getUserById(pb.getSid());  //根据这个 sid，去查询用户的所有信息
        if(ub!=null&&ub.getGid()!=null){            //先判断 不等于空
            GradeBean gb = userMapper.getGradeByGid(ub.getGid());//查询该学生所在班级
            PmxBean pmxBean  = new PmxBean();
            pmxBean.setPid(pb.getId()); //设置pid，请假流程的 pid
            pmxBean.setStatus(0);       //默认状态是 0 ，就是正在审核中
            pmxBean.setPstatus(1);      //这个是表示该那个角色处理了。一层一层进行的。
            pmxBean.setPshunxu(1);
            pmxBean.setUserid(gb.getTid());//
            userMapper.insertProcessPmx(pmxBean);
            pmxBean.setPshunxu(2);
            pmxBean.setPstatus(0);
            pmxBean.setUserid(gb.getFid());
            userMapper.insertProcessPmx(pmxBean);
            if(pb.getQjtime() >1){
                pmxBean.setPshunxu(3);
                pmxBean.setUserid(1);
                userMapper.insertProcessPmx(pmxBean);
            }
            if(pb.getQjtime()>3){
                pmxBean.setPshunxu(4);
                pmxBean.setUserid(2);
                userMapper.insertProcessPmx(pmxBean);
            }
        }
    }

    @Override
    public List<QjVo> getStuQjListBySid(Integer sid) {
        List<QjVo> list = userMapper.getStuQjListBySid(sid);
        //开始判断
        for (QjVo qjVo : list) {
            Integer qjstatus = qjVo.getQjstatus();//状态提出来
            Integer userid=0;
            if (qjstatus == 0){ //表示正在审核中
                qjVo.setStatusStr("正在审核中");
                userid = userMapper.getUserIdByPid(qjVo.getId());

            }else if (qjstatus == 1){ //表示审核通过
                qjVo.setStatusStr("审核通过");
                //通过就是审核码最大的那个，到最后一步了。
                userid = userMapper.getUserIdByPidMaxShunxu(qjVo.getId());
            }else {         //否则就是未通过！
                qjVo.setStatusStr("审核未通过");
                userid = userMapper.getUserIdByPidNopass(qjVo.getId());
            }
            QjVo vvo = userMapper.getUnameAndRnameById(userid);
            qjVo.setUname(vvo.getUname());
            qjVo.setRname(vvo.getRname());
        }
        return list;
    }

    @Override
    public List<QjVo> getQjshListByUserid(Integer id) {
        /**
         * 去查询有没有需要我审核的流程id的集合
         */
        List<Integer> pids = userMapper.getPidsByUserid(id);
        System.out.println("List<Integer> pids ="+pids);
        List<QjVo> list = null;
        if(pids!=null&&pids.size()>=1){
            list = new ArrayList<QjVo>();
            for (Integer pid : pids) {
                /**
                 * 先按照流程id去查询流程表里面有的信息
                 */
                QjVo vo = userMapper.getProcessById(pid);
                /**
                 * 查询出来的vo中，只有学生的id，没有学生名字和班级的名字
                 * 又要去查询这个学生的另外两个字段
                 */
                System.out.println("sid="+vo.getSid());
                QjVo voo = userMapper.getStuInfoBySid(vo.getSid());
                vo.setUname(voo.getUname());
                vo.setGname(voo.getGname());
                list.add(vo);
            }
        }
        return list;
    }

    @Override
    public void saveWdsh(Integer pid, Integer shstatus,Integer userid) {
         // 保存我的审核-------------
        if (shstatus==1){
            Integer pshunxu = userMapper.getQjMxInfo(pid,userid);
            Integer maxpshunxu = userMapper.getMaxPshunxu(pid);
            /**
             * 他俩要是相等，就是最后一步了，要是不相等，表示不是最后一步审核
             */
            if(pshunxu==maxpshunxu){
                userMapper.updateProcessStatus(pid,shstatus);
            }else{
                /**
                 * 不通过的话，把流程明细改成一下，然后把任务交给下一个
                 */
                /**
                 * 流程交给下一步，因为有自己的流程顺序码，+1，就是下一个人
                 */
                userMapper.updatePmxShunxu(pid,pshunxu+1);
            }
            userMapper.updatePmxStatus(pid,userid,shstatus);
        }else{
            /**
             * 审核不通过，直接把流程该更流程明细和流程就OK啦，直接结束流程
             * 直接改成两张表就ok
             * 要是想写方法复用，那么就不要在xml中把状态写死，传递给xml
             */
            userMapper.updateProcessStatus(pid,shstatus);
            userMapper.updatePmxStatus(pid,userid,shstatus);
        }
    }


}
