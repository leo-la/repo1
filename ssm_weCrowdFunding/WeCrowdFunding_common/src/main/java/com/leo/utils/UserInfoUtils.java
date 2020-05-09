package com.leo.utils;

import com.leo.manager.dao.IUserDao;
import com.leo.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.Collection;

/**
 * 用户信息工具
 */
public class UserInfoUtils {
    @Autowired
    static IUserDao userDao;

    public static void addUserInfo(Model model){
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = userDetails.getUsername();
//        UserInfo user = userDao.findUserByName(username);
//        System.out.println("userutils:"+user);
//        model.addAttribute("user",user);
//        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//        System.out.println("userutils:"+user+"  authorities");
//        model.addAttribute("authorities",authorities);
    }
}
