package com.lening.controller;

import com.alibaba.fastjson.JSON;
import com.lening.entity.PowerBean;
import com.lening.entity.RoleBean;
import com.lening.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创作时间：2020/4/10 10:37
 * 作者：李增强
 */
@Controller
public class RoleController {
    @Resource
    private RoleService roleService;


    @RequestMapping("/getRoleList")
    public String getRoleList(Model model){
        List<RoleBean> list = roleService.getRoleList();
        model.addAttribute("list", list);
        return "role_list";
    }

    @RequestMapping("/toRolePower")
    public String toRolePower(Integer rid,Model model){
        List<PowerBean> list = roleService.getRolePower(rid);
        /**
         * 犯了一个低级错误，role_power页面，是把数据给js的，需要的json啊，不能直接给list
         *
         */
        String json = JSON.toJSONString(list);

        model.addAttribute("json", json);
        model.addAttribute("rid", rid);
        return "role_power";
    }


    @RequestMapping("/saveRolePower")
    public String saveRolePower(Integer rid,String ids){
        roleService.insertRolePower(rid,ids);
        return "redirect:getRoleList.do";
    }
}
