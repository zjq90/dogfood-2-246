package com.weibo.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 分页实体类
 * 用于封装分页数据
 */
@Data
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 每页显示数量
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Integer totalCount;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 当前页数据集合
     */
    private List<T> objects;

    public Page() {
    }

    public Page(Integer totalPage, Integer pageSize, Integer totalCount, Integer currentPage, List<T> objects) {
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.objects = objects;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        if (this.totalCount != null && this.totalCount > 0 && pageSize != null && pageSize > 0) {
            this.totalPage = this.totalCount % this.pageSize == 0 
                ? this.totalCount / this.pageSize 
                : this.totalCount / this.pageSize + 1;
        }
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        if (this.pageSize != null && this.pageSize > 0 && totalCount != null && totalCount > 0) {
            this.totalPage = this.totalCount % this.pageSize == 0 
                ? this.totalCount / this.pageSize 
                : this.totalCount / this.pageSize + 1;
        }
    }
}
