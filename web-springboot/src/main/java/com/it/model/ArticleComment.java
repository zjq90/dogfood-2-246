package com.it.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 鏂囩珷璇勮瀹炰綋绫? * 瀵瑰簲鏁版嵁搴撹〃: a_comment
 */
@Data
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 璇勮ID
     */
    private Integer commentId;

    /**
     * 鏂囩珷ID
     */
    private Integer articleId;

    /**
     * 璇勮鍐呭
     */
    private String comMsg;

    /**
     * 璇勮鐢ㄦ埛ID
     */
    private Integer userComId;

    /**
     * 璇勮鐢ㄦ埛澶村儚
     */
    private String userComImg;

    /**
     * 璇勮鐢ㄦ埛鏄电О
     */
    private String userComNick;

    /**
     * 璇勮鐐硅禐鏁?     */
    private Integer comStar;

    /**
     * 璇勮鏃堕棿
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date comTime;

    /**
     * 褰撳墠鐢ㄦ埛鐐硅禐鐘舵€?0-鏈偣璧? 1-宸茬偣璧?
     */
    private Integer starStatus;
}
