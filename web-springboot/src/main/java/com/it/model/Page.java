package com.it.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 鍒嗛〉瀹炰綋绫? * 鐢ㄤ簬灏佽鍒嗛〉鏁版嵁
 */
@Data
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 鎬婚〉鏁?     */
    private Integer totalPage;

    /**
     * 姣忛〉鏄剧ず鏁伴噺
     */
    private Integer pageSize;

    /**
     * 鎬昏褰曟暟
     */
    private Integer totalCount;

    /**
     * 褰撳墠椤?     */
    private Integer currentPage;

    /**
     * 褰撳墠椤垫暟鎹泦鍚?     */
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
