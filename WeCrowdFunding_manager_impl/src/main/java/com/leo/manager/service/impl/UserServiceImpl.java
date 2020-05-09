package com.leo.manager.service.impl;

import com.leo.manager.dao.IUserDao;
import com.leo.manager.service.IUserService;
import com.leo.pojo.Member;
import com.leo.pojo.Role;
import com.leo.pojo.UserInfo;
import com.leo.utils.EncoderUtils;
import com.leo.utils.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userDao.findUserByName(username);

        List<GrantedAuthority> list = new ArrayList<>();

        if(userInfo==null){
            Member member = userDao.findMemberByName(username);
            if(member==null){
                throw new BadCredentialsException("用户名不存在");
            }else{
                GrantedAuthority grantedAuthority = new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ROLE_MEMBER";
                    }
                };
                list.add(grantedAuthority);
                return new User(username, member.getPassword(), list);
            }
        }
        List<Role> roles = userDao.findUserRoles(userInfo.getId());



        for (final Role role : roles) {
            GrantedAuthority grantedAuthority = new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return "ROLE_"+role.getName().split("-")[0].trim();
                }
            };
            list.add(grantedAuthority);
        }

        for (GrantedAuthority grantedAuthority : list) {
            System.out.println("用户角色:"+grantedAuthority.getAuthority());
        }



        return new User(username, userInfo.getPassword(), list);
    }

    @Override
    public boolean findUserByAuth(UserInfo userInfo) {
        System.out.println("进入验证身份："+userInfo);
        UserInfo user = userDao.findUserByAuth(userInfo);
        if(user==null){
            return false;
        }else {
            return true;
        }

    }

    @Override
    public boolean findUserByAcct(UserInfo userInfo) {
        UserInfo user = userDao.findUserByAcct(userInfo);
        if(user!=null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void addUser(UserInfo userInfo) {
        System.out.println("注册用户："+userInfo);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        userInfo.setPassword(EncoderUtils.PasswordEncoder(userInfo.getPassword()));
        userInfo.setCreatetime(time);
        userInfo.setAuthorities("0");
        userInfo.setUsertype("0");
        userDao.addUser(userInfo);
    }

    @Override
    public boolean addMember(Member member) {
        member.setPassword(EncoderUtils.PasswordEncoder(member.getPassword()));
        if(member.getLoginacct()==null){
            member.setLoginacct(member.getUsername());
        }
        if(member.getAuthstatus()==null){
            member.setAuthstatus("0");
        }
        if(member.getUsertype()==null){
            member.setUsertype("0");
        }
        try{
            Integer integer = userDao.addMember(member);
            if(integer==1){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public Integer findUserIdByName(String name) {
        Integer id = userDao.findUserIdByName(name);
        return id;
    }

    @Override
    public Integer updateMemberByMid(Member member) {
        Integer integer = userDao.updateMemberByMid(member);
        return integer;
    }

    @Override
    public UserInfo findUserByName(String name) {
        UserInfo user = userDao.findUserByName(name);
        return user;
    }

    @Override
    public Member findMemberById(Integer id) {
        Member member = userDao.findMembeById(id);
        return member;
    }

    @Override
    public Member findMemberByName(String name) {
        Member member = userDao.findMemberByName(name);
        return member;
    }

    @Override
    public boolean updateUserAvatar(String avatarpath,Integer id,String usertype) {
        Integer integer = 0;
        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = applicationContext.getServletContext();
        if(usertype.equals("0")){
            integer = userDao.updateMemberAvatar(avatarpath, id);
            Member user = (Member) application.getAttribute("user");
            Member newUser = userDao.findMemberByName(user.getUsername());
            application.setAttribute("user",newUser);

        }else if(usertype.equals("1")){
            integer = userDao.updateUserAvatar(avatarpath, id);

            UserInfo user = (UserInfo) application.getAttribute("user");
            UserInfo newUser = userDao.findUserByName(user.getUsername());
            application.setAttribute("user",newUser);
        }
        if(integer==0){
            return false;
        }else if(integer==1){
            return true;
        }else {
            throw new RuntimeException("存在多个数据");
        }
    }

    @Override
    public void updateStatus(String authstatus) {
        userDao.updateAuthstatus(authstatus,((Member)StorageUtils.getRequest().getSession().getAttribute("user")).getId());
    }

    @Override
    public void updateStatusByid(String authstatus, Integer id) {
        userDao.updateAuthstatus(authstatus,id);
    }

    @Override
    public void updateUserInfo() {
        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = applicationContext.getServletContext();
        Member user = (Member) application.getAttribute("user");
        Member member = userDao.findMemberByName(user.getUsername());
        application.setAttribute("user",member);
    }

}
