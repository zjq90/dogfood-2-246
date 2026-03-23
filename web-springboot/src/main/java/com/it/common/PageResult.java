package com.it.common;

import lombok.Data;
import java.io.Serializable;
import java.util.List;


@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Integer pageNum;

    
    private Integer pageSize;

    
    private Long total;

    
    private Integer pages;

    
    private List<T> list;

    public PageResult() {
    }

    public PageResult(Integer pageNum, Integer pageSize, Long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
        this.pages = (int) Math.ceil((double) total / pageSize);
    }

    public static <T> PageResult<T> of(Integer pageNum, Integer pageSize, Long total, List<T> list) {
        return new PageResult<>(pageNum, pageSize, total, list);
    }
}
