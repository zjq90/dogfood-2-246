package com.weibo.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装类
 * 用于封装分页查询的返回数据
 *
 * @param <T> 数据类型
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 是否有下一页
     */
    private Boolean hasNextPage;

    /**
     * 默认构造方法
     */
    public PageResult() {
    }

    /**
     * 全参数构造方法
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @param total    总记录数
     * @param list     数据列表
     */
    public PageResult(Integer pageNum, Integer pageSize, Long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
        this.pages = (int) Math.ceil((double) total / pageSize);
        this.hasNextPage = pageNum < this.pages;
    }

    /**
     * 创建分页结果
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @param total    总记录数
     * @param list     数据列表
     * @param <T>      数据类型
     * @return PageResult对象
     */
    public static <T> PageResult<T> of(Integer pageNum, Integer pageSize, Long total, List<T> list) {
        return new PageResult<>(pageNum, pageSize, total, list);
    }

    /**
     * 创建空分页结果
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @param <T>      数据类型
     * @return PageResult对象
     */
    public static <T> PageResult<T> empty(Integer pageNum, Integer pageSize) {
        return new PageResult<>(pageNum, pageSize, 0L, null);
    }
}
