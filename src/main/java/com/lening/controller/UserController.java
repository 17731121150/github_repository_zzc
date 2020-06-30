package com.lening.controller;

import com.alibaba.fastjson.JSON;
import com.lening.entity.*;
import com.lening.service.UserService;
import com.lening.utils.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @RestController注解是Controller+ResponseBody
 * 在类上面写的话，那整合类里面的方法全部就是返回的是json字符串，整合项目都是前后端分离
 * 互联网项目，我们这个项目是一个传统的软件项目，我们返回的是    页面地址
 */


@Controller
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 保存我的审核
     */
    @RequestMapping("/saveWdsh")
    public String saveWdsh(HttpServletRequest request,Integer pid,Integer shstatus){
        UserBean ub = (UserBean)request.getSession().getAttribute("ub");
        userService.saveWdsh(pid,shstatus,ub.getId());
        return "redirect:getQjShList.do";
    }

    //管理登录上来查询我的审核
    @RequestMapping("/getQjShList")
    public String getQjShList(HttpServletRequest request,Model model){
        UserBean ub = (UserBean)request.getSession().getAttribute("ub");
        System.out.println("getQjShList方法中拿到userid="+ub.getId());
        List<QjVo> list = userService.getQjshListByUserid(ub.getId());
        model.addAttribute("list", list);
        return "qjsh_list";
    }

    /**
     * 这个注解虽然不是spring的但是他由Spring来管理啊
     * 400错误，说明类型对不上，日期类型不一样，需要日累类型转换
     * 直接使用实体类来接收，需不需要来转换
     * 因为我们带过来的日期有小时，还有日期需要处理
     * 在控制层处理，也可以在entity里面处理
     */
    @RequestMapping("/saveStuQj2")
    public String saveStuQj2(ProcessBean pb){
        userService.saveStuQj2(pb);
        return "redirect:getStuJtList.do";
    }
    //学生提交 请假
//    @RequestMapping("/saveStuQj")
//    public String saveStuQj(Integer id, Double qjtime, Date stime,Date etime,String qjcause){
//        /**
//         * 接下来则么处理，开始走我们的业务流程，三个流程，属于业务型的代码，所以我们可以全部在sevice层搞定
//         */
//        userService.saveStuQj(id,qjtime,stime,etime,qjcause);
//
//        return "";
//    }


    /**
     * getStuJtList.do.树--我 的请假
     */
    @RequestMapping("/getStuJtList")
    public String getStuJtList(HttpServletRequest request,Model model){
        /**
         * 需要把这个学生的情况请查询到，然后再去查询这个学的请假情况，我们先直接去去页面，发起请假
         */
        UserBean ub = (UserBean)request.getSession().getAttribute("ub");
        List<QjVo> list = userService.getStuQjListBySid(ub.getId());
        System.out.println("查询自己的list="+list);
        model.addAttribute("list", list);
        return "stuqj_list";
    }


    /**
     * 转发到学生请假的页面
     */
    @RequestMapping("/toStuQj")
    public String toStuQj(HttpServletRequest request,Model model){
        UserBean ub = (UserBean)request.getSession().getAttribute("ub");
        model.addAttribute("stu", ub);
        return "stu_qj";
    }









    /**
     * 登录成功了去main.jsp,登录失败了会index.jsp
     * 登录成功了，需要把用户的信息存进session里面
     */
    @RequestMapping("/getLogin")
    public String getLogin(UserBean userBean, HttpServletRequest request){
        /**
         * 登录的返回，要是想明确告诉用户是用户名为空，密码错误等详细信息，那么就不能这样用，
         * 一般登录是告诉登录失败
         */
        UserBean ub = userService.getLogin(userBean);
        if(ub!=null){
            request.getSession().setAttribute("ub", ub);
            return "main";
        }
        return "../../index";
    }

    @RequestMapping("/getRlistJosn")
    @ResponseBody
    public List<RoleBean> getRlistJosn(Integer deptid){
        List<RoleBean> rlist = userService.getRoleListByDeptid2(deptid);
        /**
         * ajax回传值，可以只用response打回去，这是servlet的做法。
         * SpringMVC可以支持，直接打回去，需要自己转json，springMVC也可以转json
         */
        System.out.println(rlist);
        return rlist;
    }

    @RequestMapping("/saveUserDeptRole")
    public String saveUserDeptRole(Integer id,Integer deptid,Integer rid){
        userService.saveUserDeptRole(id,deptid,rid);
        return "redirect:getUserList.do";
    }

    /**
     * 去给员工分配部门和角色
     * 传过来的参数只有员工id
     * 需要的参数
     * 1、员工的全部信息，需要查询出员工已有的部门和角色（其实只需要部门和角色的id就OK啦，用来回显）
     * 2、全部部门列表
     * 3、角色列表（用户现在所在的部门的所有角色列表）
     */
    @RequestMapping("/toUserDeptRole")
    public String toUserDeptRole(Integer id,Model model){
        //1、查询员工信息
        UserBean userBean = userService.getUserById(id);
        //2、查询员工列表
        List<DeptBean> dlist = userService.getDeptList();
        //3、查询员工所在部门的拥有的全部角色，有可能这个员工目前还没有部门或者没有角色
        List<RoleBean> rlist = userService.getRoleListByDeptid(userBean);
        model.addAttribute("ub", userBean);
        model.addAttribute("dlist", dlist);
        model.addAttribute("rlist", rlist);
        return "user_deptrole";
    }

    @RequestMapping("/getUserList")
    public String getUserList(Model model){

        /**
         * 这个参数就是当前页，从页面传过来
         */
        String yema = "2";

        /**
         * 每页显示多少条记录，可以从页面传递过来，要是页面允许用户选择每页显示多少条记录，从页面传递过来，
         * 普通项目，一般页面大小是固定的，不允许用户自己选择，在后台写死，不给也行
         */
        String pagesize="3";
        //假设有10条，这个需要自己查询
        Integer count=10;

        /**
         * 只要把这三个参数给page，基本全部信息就可以查出来了
         */
        Page page = new Page(yema, count, pagesize);
        

        List<UserBean> list = userService.findAll();
        model.addAttribute("list", list);
        return "user_list";
    }

    @RequestMapping("/getPowerJson")
    public String getPowerJson(Model model,HttpServletRequest request){
        /**
         * 获取sesion的时候，可以传递boolean类型的参数，不写默认是true，
         * 要是有就返回原来的，要是没有重新创建
         * 要是设置成false，有的话，返回，没有的话，返回null
         */
        UserBean ub = (UserBean)request.getSession(true).getAttribute("ub");
        if(ub!=null){

            /**
             * 这个list里面目前是有按钮的，给菜单不能有按钮，但是我们给urls必须有按钮，要不查询两次，查询两次没什么可说的，直接查
             * 我们就只想查询一次
             */
            List<PowerBean> list = userService.getPowerListById(ub.getId());
            /**
             * 可以直接去数据库把用户拥有的url查出来，也可以直接遍历这list，把url拿出来
             *
             */
            Set<String> urls = new HashSet<String>();
            /**
             * 没有把list的大小固定，每次会自动去获取list的大小
             */
            for (int x = 0; x<list.size(); x++) {
                PowerBean pb =  list.get(x);
                if(pb.getUrl()!=null){
                    urls.add(pb.getUrl().trim());
                }
                /**
                 * 把url放进urls里面之后，再次判断，要是这个是按钮，把他从list里面删掉
                 * 要把是按钮的删掉
                 */

                if("是".equals(pb.getIsbutton())){
                    /**
                     * list的删除，可以把pb给他，也可以把角标给他
                     * 他的大小会在自动减一
                     */
                    list.remove(pb);
                    /**
                     * 删除完之后，需要把list的大小减一，要不然集合下标越界，并且要把指针在回减一个，
                     * 比如现在角标是3，把3删掉后，4就会自动变成3，但是循环里面已经到4了，所以就会把原来的4没越过去了
                     *
                     */
                    x--;//不会减角标，只能一部门
                }
            }
            request.getSession().setAttribute("urls", urls);
            /**
             * 转化json之前，把按钮的删掉
             */
            String json = JSON.toJSONString(list);
            model.addAttribute("json", json);
        }
        return "left";
    }
}
