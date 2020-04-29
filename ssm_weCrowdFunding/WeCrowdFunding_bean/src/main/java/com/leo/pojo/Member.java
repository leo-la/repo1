package com.leo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Member implements Serializable {
    private Integer id;
    private String loginacct;
    private String username;
    private String password;
    private String email;
    private String authstatus;
    private String usertype;
    private String realname;
    private String cardnum;
    private String telephone;
    private String accttype;
    private String avatar;
}
