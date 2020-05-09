package com.leo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeCert implements Serializable {
    private Integer id;
    private String accttype;
    private Integer certid;
}
