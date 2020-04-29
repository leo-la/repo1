package com.leo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Cert implements Serializable {
    private Integer id;
    private String name;
}
