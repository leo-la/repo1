package com.leo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberCert implements Serializable {
    private Integer id;
    private Integer memberid;
    private Integer certid;
    private String iconpath;
}
