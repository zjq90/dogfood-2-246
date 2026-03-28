package com.it.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 鐢ㄦ埛瀹炰綋绫? * 瀵瑰簲鏁版嵁搴撹〃: user
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 鐢ㄦ埛ID
     */
    private Integer userId;

    /**
     * 鐢ㄦ埛鍚?閭)
     */
    private String username;

    /**
     * 瀵嗙爜(MD5鍔犲瘑)
     */
    private String password;

    /**
     * 鐢ㄦ埛鏉冮檺(0-鏅€氱敤鎴? 1-绠＄悊鍛?
     */
    private Integer status;

    /**
     * 鏄惁琚妇鎶?0-鏈妇鎶? 1-琚妇鎶?
     */
    private Integer reported;

    /**
     * 灏佸彿鎴鏃堕棿
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date titleTime;

    /**
     * 鐢ㄦ埛澶村儚璺緞
     */
    private String portrait;

    /**
     * 涓€х鍚?     */
    private String signature;

    /**
     * 鑷垜浠嬬粛
     */
    private String selfIntroduc;

    /**
     * 鏄电О
     */
    private String nickname;

    /**
     * 鐢ㄦ埛鍦板潃
     */
    private String address;

    /**
     * 鐢ㄦ埛娉ㄥ唽鏃堕棿
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    /**
     * 鎵惧洖瀵嗙爜鍑瘉(UUID)
     */
    private Integer code;

    /**
     * 鎵惧洖瀵嗙爜鏃堕棿闄愬埗
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date outTime;
}
