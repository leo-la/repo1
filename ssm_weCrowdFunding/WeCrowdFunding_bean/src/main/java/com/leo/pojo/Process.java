package com.leo.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
public class Process implements Serializable {
    private String id;
    private String name;
    private String key;
    private Integer version;
    private String taskid;
    private String username;
    private Integer userid;
}
