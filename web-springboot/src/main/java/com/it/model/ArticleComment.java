package com.it.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


@Data
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Integer commentId;

    
    private Integer articleId;

    
    private String comMsg;

    
    private Integer userComId;

    
    private String userComImg;

    
    private String userComNick;

    
    private Integer comStar;

    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date comTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    
    private Integer starStatus;
}
