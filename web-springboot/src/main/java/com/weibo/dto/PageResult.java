package com.weibo.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装类
 * 用于封装分页查询的结果数据
 * 
 * @param <T> 数据类型
 * @author weibo Team
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 总记录数
     */
    private Integer total;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 无参构造函数
     */
    public PageResult() {
    }

    /**
     * 全参构造函数
     */
    public PageResult(List<T> list, Integer total, Integer pageNum, Integer pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = (total + pageSize - 1) / pageSize;
    }

    /**
     * 计算总页数
     */
    public static int calculatePages(int total, int pageSize) {
        return (total + pageSize - 1) / pageSize;
    }
}
