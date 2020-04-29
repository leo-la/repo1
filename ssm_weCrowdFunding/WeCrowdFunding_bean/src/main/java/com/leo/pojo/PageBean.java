package com.leo.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageBean implements Serializable {
    private Integer currentPage;
    private Integer totalPage;
    private Integer pageSize;
    private Integer totalCount;
    private List pageData;
    private List pageData2;
    private String str;
}
