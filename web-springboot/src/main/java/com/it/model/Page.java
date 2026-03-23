package com.it.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;


@Data
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Integer totalPage;

    
    private Integer pageSize;

    
    private Integer totalCount;

    
    private Integer currentPage;

    
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
