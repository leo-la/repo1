package com.leo.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserInfo implements Serializable {
    private Integer id;
    private String loginacct;
    private String password;
    private String username;
    private String email;
    private String name;
    private String createtime;
    private String authorities;
    private String usertype;
    private String avatar;

}
