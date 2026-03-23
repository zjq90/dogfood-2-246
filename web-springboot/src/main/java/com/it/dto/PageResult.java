package com.weibo.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装类
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页数据
     */
    private List<T> list;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    public PageResult() {
    }

    public PageResult(List<T> list, Long total, Integer pages, Integer pageNum, Integer pageSize) {
        this.list = list;
        this.total = total;
        this.pages = pages;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    /**
     * 构建分页结果
     */
    public static <T> PageResult<T> build(List<T> list, Long total, Integer pageNum, Integer pageSize) {
        Integer pages = (int) Math.ceil((double) total / pageSize);
        return new PageResult<>(list, total, pages, pageNum, pageSize);
    }
}
