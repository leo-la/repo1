package com.leo.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PermissionNode extends Node implements Serializable  {
    private Integer id;
    private Integer pid;
    private String name;
    private String icon;
    private String url;
//    private String seqno;
    private boolean open;
    private boolean checked;
    private List<PermissionNode> children;
}
