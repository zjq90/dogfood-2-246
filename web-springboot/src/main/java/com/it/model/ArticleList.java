package com.it.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


@Data
public class ArticleList implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Integer articleId;

    
    private String title;

    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishedTime;

    
    private String tagName;

    
    private Integer authorId;

    
    private String authorImg;

    
    private String authorNick;

    
    private Integer shareNum;

    
    private Integer starNum;

    
    private Integer commentNum;

    
    private Integer collectionNum;

    
    private String content;

    
    private String firstImg;

    
    private Integer stick;

    
    private Integer pageView;
}
