package com.it.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 鏂囩珷鍥炲瀹炰綋绫? * 瀵瑰簲鏁版嵁搴撹〃: a_reply
 */
@Data
public class ArticleReply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 鍥炲ID
     */
    private Integer replyId;

    /**
     * 璇勮ID
     */
    private Integer commentId;

    /**
     * 鍥炲鍐呭
     */
    private String replyMsg;

    /**
     * 鍥炲鑰呯敤鎴稩D
     */
    private Integer userReplyFromId;

    /**
     * 琚洖澶嶇敤鎴稩D
     */
    private Integer userReplyToId;

    /**
     * 鍥炲鑰呭ご鍍?     */
    private String userReplyImg;

    /**
     * 琚洖澶嶈€呮樀绉?     */
    private String userReplyToNick;

    /**
     * 鍥炲鑰呮樀绉?     */
    private String userReplyFromNick;

    /**
     * 鍥炲鐐硅禐鏁?     */
    private Integer replyStar;

    /**
     * 鍥炲鏃堕棿
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date replyTime;
}
