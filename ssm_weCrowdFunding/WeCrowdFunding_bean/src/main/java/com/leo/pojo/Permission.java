package com.leo.pojo;

import lombok.Data;

import java.io.Serializable;
@Data
public class Permission implements Serializable {
    private Integer id;
    private Integer pid;
    private String name;
    private String icon;
    private String url;
}

