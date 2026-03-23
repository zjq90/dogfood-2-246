package com.weibo.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<T> list;
    private Integer total;
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPages;
    
    public PageResult() {
    }
    
    public PageResult(List<T> list, Integer total, Integer pageNum, Integer pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPages = (total + pageSize - 1) / pageSize;
    }
}
