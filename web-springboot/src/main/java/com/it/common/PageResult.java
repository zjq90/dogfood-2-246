package com.it.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果封装类
 * 
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    
    /**
     * 当前页码
     */
    private Integer pageNum;
    
    /**
     * 每页大小
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
    private Boolean hasNext;
    
    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;
    
    /**
     * 构造分页成功结果
     *
     * @param pageNum  当前页码
     * @param pageSize 每页大小
     * @param total    总记录数
     * @param list     数据列表
     * @param <T>      数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> success(Integer pageNum, Integer pageSize, Long total, List<T> list) {
        PageResult<T> result = new PageResult<>();
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setTotal(total);
        result.setList(list);
        
        // 计算总页数
        int pages = (int) Math.ceil((double) total / pageSize);
        result.setPages(pages);
        
        // 计算是否有上一页和下一页
        result.setHasPrevious(pageNum > 1);
        result.setHasNext(pageNum < pages);
        
        return result;
    }
}
