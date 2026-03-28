package com.it.model;

import lombok.Data;

import java.util.Date;

/**
 * 鏂囩珷鏀惰棌瀹炰綋绫? * 
 * @author weibo Team
 */
@Data
public class ArticleCollection {
    
    /**
     * 鏀惰棌ID
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
