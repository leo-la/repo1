package com.leo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthProcess implements Serializable {
    private Integer id;
    private Integer mid;
    private String accttype;
    private String code;
    private String status;
    private String proid;
}
