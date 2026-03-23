package com.weibo.controller;

import com.weibo.util.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/verify")
public class VerifyCodeController {

    /**
     * 生成验证码图片
     */
    @GetMapping("/code")
    public void getVerifyCode(HttpSession session, HttpServletResponse response) throws IOException {
        log.debug("生成验证码");
        
        VerifyCodeUtil verifyCode = new VerifyCodeUtil();
        BufferedImage image = verifyCode.getImage();
        String text = verifyCode.getText();
        
        session.setAttribute("verifyCode", text);
        
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        
        VerifyCodeUtil.output(image, response.getOutputStream());
    }

    /**
     * 验证验证码
     */
    @GetMapping("/check")
    public boolean checkVerifyCode(@org.springframework.web.bind.annotation.RequestParam String code, HttpSession session) {
        String sessionCode = (String) session.getAttribute("verifyCode");
        if (sessionCode == null) {
            return false;
        }
        boolean result = sessionCode.equalsIgnoreCase(code);
        if (result) {
            session.removeAttribute("verifyCode");
        }
        return result;
    }
}
