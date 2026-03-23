package com.it.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


@Data
public class ArticleReply implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Integer replyId;

    
    private Integer commentId;

    
    private String replyMsg;

    
    private Integer userReplyFromId;

    
    private Integer userReplyToId;

    
    private String userReplyImg;

    
    private String userReplyToNick;

    
    private String userReplyFromNick;

    
    private Integer replyStar;

    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date replyTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer userId;
}
