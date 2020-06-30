package com.lening.controller;

import com.lening.entity.DeptBean;
import com.lening.entity.RoleBean;
import com.lening.service.DeptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创作时间：2020/4/9 16:26
 * 作者：李增强
 */
@Controller
public class DeptController {
    @Resource
    private DeptService deptService;

    @RequestMapping("/getDeptList")
    public String getDeptList(Model model){
        List<DeptBean> list = deptService.getDeltList();
        model.addAttribute("list", list);
        return "dept_list";
    }

    @RequestMapping("/saveDeptRole")
    public String saveDeptRole(Integer deptid,Integer[] rids){
        deptService.saveDeptRole(deptid,rids);
        return "redirect:getDeptList.do";
    }

    /**
     * 去给部门选择角色
     * @param deptid
     * @param model
     * @return
     */
    @RequestMapping("/toDeptRole")
    public String toDeptRole(Integer deptid,Model model){
        DeptBean db = deptService.getDeptByDeptid(deptid);
        List<RoleBean> list = deptService.getRoleList();
        /**
         * 当前部门有的角色的rid，需要在前台页面的复选框中回显使用
         */
        List<Integer> rids = deptService.RidsByDeptid(deptid);
        model.addAttribute("db", db);
        model.addAttribute("list", list);
        model.addAttribute("rlist", rids);
        return "dept_role";
    }
}
