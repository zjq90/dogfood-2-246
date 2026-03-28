package com.it.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 鏂囩珷鍒楄〃瀹炰綋绫? * 鐢ㄤ簬鏂囩珷鍒楄〃灞曠ず
 */
@Data
public class ArticleList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 鏂囩珷ID
     */
    private Integer articleId;

    /**
     * 鏂囩珷鏍囬
     */
    private String title;

    /**
     * 鍙戣〃鏃堕棿
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishedTime;

    /**
     * 鏂囩珷鍒嗙被鍚?     */
    private String tagName;

    /**
     * 浣滆€匢D
     */
    private Integer authorId;

    /**
     * 浣滆€呭ご鍍?     */
    private String authorImg;

    /**
     * 浣滆€呮樀绉?     */
    private String authorNick;

    /**
     * 杞彂鏁?     */
    private Integer shareNum;

    /**
     * 鐐硅禐鏁?     */
    private Integer starNum;

    /**
     * 璇勮鏁?     */
    private Integer commentNum;

    /**
     * 鏀惰棌鏁?     */
    private Integer collectionNum;

    /**
     * 鏂囩珷鍐呭棰勮
     */
    private String content;

    /**
     * 鏂囩珷绗竴寮犲浘鐗?     */
    private String firstImg;

    /**
     * 鏄惁缃《
     */
    private Integer stick;

    /**
     * 娴忚閲?     */
    private Integer pageView;
}
