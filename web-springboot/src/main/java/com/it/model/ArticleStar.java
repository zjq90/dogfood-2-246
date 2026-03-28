package com.it.model;

import lombok.Data;

import java.util.Date;

/**
 * 鏂囩珷鐐硅禐瀹炰綋绫? * 
 * @author weibo Team
 */
@Data
public class ArticleStar {
    
    /**
     * 鐐硅禐ID
     */
    private Integer id;
    
    /**
     * 鐢ㄦ埛ID
     */
    private Integer userId;
    
    /**
     * 鏂囩珷ID
     */
    private Integer articleId;
    
    /**
     * 鍒涘缓鏃堕棿
     */
    private Date createTime;
}
