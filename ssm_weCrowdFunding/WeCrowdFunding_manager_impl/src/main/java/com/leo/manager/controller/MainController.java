package com.leo.manager.controller;

import com.leo.manager.service.IMainService;
import com.leo.manager.service.IUserService;
import com.leo.manager.service.impl.UserServiceImpl;
import com.leo.pojo.AssignRoleBean;
import com.leo.pojo.PageBean;
import com.leo.pojo.UserInfo;
import com.leo.utils.UserInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {
    @Autowired
    IMainService mainService;
    @Autowired
    IUserService userService;

    @RequestMapping("/toMain")
    public String toMain(Model model){
        UserInfoUtils.addUserInfo(model);
        return "main";
    }

    @RequestMapping("/toAuthCert")
    public String toAuthCert(Model model){
        UserInfoUtils.addUserInfo(model);
        return "auth_cert";
    }

    @RequestMapping("/toAuthAdv")
    public String toAuthAdv(Model model){
        UserInfoUtils.addUserInfo(model);
        return "auth_adv";
    }

    @RequestMapping("/toAuthProject")
    public String toAuthProject(Model model){
        UserInfoUtils.addUserInfo(model);
        return "auth_project";
    }


    @RequestMapping("/toParam")
    public String toParam(Model model){
        UserInfoUtils.addUserInfo(model);
        return "param";
    }








}
