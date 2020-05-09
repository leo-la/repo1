package com.leo.manager.controller;

import com.leo.manager.dao.IUserDao;
import com.leo.manager.service.IUserService;
import com.leo.pojo.Member;
import com.leo.pojo.UserInfo;
import com.leo.utils.FileUtils;
import com.leo.utils.ImgUtils;
import com.leo.utils.UserInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collection;
import java.util.UUID;

/**
 * 用户控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserDao userDao;
    /**
     * 用户业务
     */
    @Autowired
    IUserService userService;
    /**
     * 用户上传文件地址
     */
    @Value("${ava}")
    private String staticAccessPath;

    /**
     * 跳转首页
     * @return
     */
    @RequestMapping("/toIndex")
    public String toIndex(){
        return "/index";
    }

    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 跳转个人页面
     * @param model
     * @return
     */
    @RequestMapping("/toPersonal")
    public String toUser(Model model){
        UserInfoUtils.addUserInfo(model);
        model.addAttribute("path",staticAccessPath);
        return "member";
    }

    /**
     * 跳转消息模板
     * @param model
     * @return
     */
    @RequestMapping("/toMessage")
    public String toMessage(Model model){
        UserInfoUtils.addUserInfo(model);
        return "message";
    }

    /**
     * 跳转修改头像
     * @param model
     * @return
     */
    @RequestMapping("/toUpdateAvatar")
    public String toUpdateAvatar(Model model){
        UserInfoUtils.addUserInfo(model);
        return "updateAvatar";
    }

    /**
     * 登录成功
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("principal:"+principal);
        UserDetails userDetails = (UserDetails) principal;
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if(authority.getAuthority().contains("SA")){
                return "redirect:/main/toMain";
            }
        }
        return "redirect:/user/toIndex";
    }

    /**
     * 登录失败
     * @param model
     * @return
     */
    @RequestMapping("/failure")
    public String failure(Model model){
        model.addAttribute("error_msg","账号或密码错误");
        return "login";
    }

    /**
     * 修改密码
     * @param userInfo
     * @return
     */
    @RequestMapping("/updatePassword")
    public String updatePassword(UserInfo userInfo){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123");
        userInfo.setUsername("leo");
        userInfo.setPassword(encode);
        userDao.updatePasswordByName(userInfo);
        return "main";
    }

    /**
     * 过期方法-检测账户类型
     * @param userInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkacct")
    public boolean checkacct(@RequestBody UserInfo userInfo){
        System.out.println("acct:"+userInfo);
        return  userService.findUserByAcct(userInfo);
    }

    /**
     * 注册账户
     * @param member
     * @return
     */
    @ResponseBody
    @RequestMapping("/register")
    public boolean register(@RequestBody Member member){
        return userService.addMember(member);
    }

    /**
     * 跳转注册页面
     * @param model
     * @return
     */
    @RequestMapping("/toRegister")
    public String toRegister(Model model){
        System.out.println("注册页面");
        return "reg";
    }

    /**
     * 加载用户信息
     * @param userInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/loadUser")
    public UserInfo loadUser(UserInfo userInfo){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        userInfo.setUsername(username);
        return userInfo;
    }

    /**
     * 更新头像
     * @param file
     * @param id
     * @param usertype
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateAvatar")
    public boolean updateAvatar(MultipartFile file,Integer id,String usertype,HttpServletRequest request){
        System.out.println("file:"+file+" id:"+id+" usertype:"+usertype);
        boolean isSuccess = false;
        try{
            String filename = file.getOriginalFilename();
            filename = UUID.randomUUID().toString().replace("-","") + "_" + filename;
            String realPath = staticAccessPath;
            isSuccess = userService.updateUserAvatar(filename,id,usertype);
            FileUtils.uploadFile(file,filename,realPath);
        }catch (Exception e){
            e.printStackTrace();
        }
        //返回json
        return isSuccess;
    }

    /**
     * 显示图片
     * @param image_name
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/image/{image_name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("image_name") String image_name){
        ResponseEntity<byte[]> responseEntity = ImgUtils.showImg(staticAccessPath, image_name);
        return responseEntity;
    }

}
