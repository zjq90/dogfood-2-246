package com.weibo.controller;

import com.weibo.dto.Result;
import com.weibo.entity.User;
import com.weibo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用户控制器
 * 处理用户登录、注册、个人信息管理等功能
 * 
 * @author weibo Team
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     */
    @GetMapping("/login")
    public String toLogin(HttpServletRequest request, Model model) {
        // 从Cookie中记住的用户名和密码
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("uname".equals(cookie.getName())) {
                    model.addAttribute("rememberedUsername", cookie.getValue());
                }
                if ("upwd".equals(cookie.getName())) {
                    model.addAttribute("rememberedPassword", cookie.getValue());
                }
            }
        }
        return "login";
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public String login(@RequestParam("uname") String username,
                       @RequestParam("upwd") String password,
                       @RequestParam(value = "remember", required = false) String remember,
                       @RequestParam(value = "auto", required = false) String auto,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       Model model) {
        logger.info("用户登录请求，用户名：{}", username);

        Result<User> result = userService.login(username, password);

        if (result.isSuccess()) {
            User user = result.getData();
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);
            session.setAttribute("uname", user.getUsername());
            session.setAttribute("userId", user.getUserId());

            // 记住用户名
            Cookie usernameCookie = new Cookie("uname", username);
            usernameCookie.setMaxAge(7 * 24 * 60 * 60);
            usernameCookie.setPath("/");
            response.addCookie(usernameCookie);

            // 记住密码
            if (remember != null) {
                Cookie passwordCookie = new Cookie("upwd", password);
                passwordCookie.setMaxAge(7 * 24 * 60 * 60);
                passwordCookie.setPath("/");
                response.addCookie(passwordCookie);
            } else {
                Cookie passwordCookie = new Cookie("upwd", "");
                passwordCookie.setMaxAge(0);
                passwordCookie.setPath("/");
                response.addCookie(passwordCookie);
            }

            // 自动登录标记
            if (auto != null) {
                Cookie autoCookie = new Cookie("auto", "auto");
                autoCookie.setMaxAge(7 * 24 * 60 * 60);
                autoCookie.setPath("/");
                response.addCookie(autoCookie);
            }

            logger.info("用户登录成功，用户名：{}", username);
            return "redirect:/page/home";
        } else {
            model.addAttribute("errorMsg", result.getMessage());
            return "login";
        }
    }

    /**
     * 跳转到注册页面
     */
    @GetMapping("/register")
    public String toRegister() {
        return "register";
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public String register(@RequestParam("uname") String username,
                          @RequestParam("upwd") String password,
                          Model model) {
        logger.info("用户注册请求，用户名：{}", username);

        Result<User> result = userService.register(username, password);

        if (result.isSuccess()) {
            logger.info("用户注册成功，用户名：{}", username);
            return "redirect:/user/login";
        } else {
                model.addAttribute("errorMsg", result.getMessage());
                model.addAttribute("username", username);
                return "register";
        }
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        session.removeAttribute("uname");
        session.removeAttribute("userId");
        session.invalidate();

        // 清除自动登录Cookie
        Cookie autoCookie = new Cookie("auto", "");
        autoCookie.setMaxAge(0);
        autoCookie.setPath("/");
        response.addCookie(autoCookie);

        logger.info("用户退出登录");
        return "redirect:/user/login";
    }

    /**
     * 跳转到个人主页
     */
    @GetMapping("/profile")
    public String toProfile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        User user = userService.getUserByUserId(userId);
        model.addAttribute("user", user);
        return "my_page";
    }

    /**
     * 更新个人信息页面
     */
    @GetMapping("/edit")
    public String toEditProfile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        User user = userService.getUserByUserId(userId);
        model.addAttribute("user", user);
        return "alter";
    }

    /**
     * 更新个人信息
     */
    @PostMapping("/edit")
    public String editProfile(@ModelAttribute User user,
                             HttpServletRequest request,
                             Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        user.setUserId(userId);
        Result<Boolean> result = userService.updatePersonage(user);

        if (result.isSuccess()) {
            logger.info("用户信息更新成功，用户ID：{}", userId);
            return "redirect:/user/profile";
        }
        return "redirect:/user/edit";
    }

    /**
     * 头像上传页面
     */
    @GetMapping("/portrait")
    public String toPortraitUpload() {
        return "portrait_upload";
    }

    /**
     * 头像上传
     */
    @PostMapping("/portrait/upload")
    @ResponseBody
    public Result<Map<String, String>> uploadPortrait(@RequestParam("file") MultipartFile file,
                                                                      HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }

        if (file.isEmpty()) {
            return Result.fail("请选择上传文件");
        }

        try {
            // 文件保存目录
            String uploadDir = request.getServletContext().getRealPath("/") + "upload" + File.separator + "portrait";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID().toString() + "." + suffix;

            // 保存文件
            File destFile = new File(uploadDir + File.separator + fileName);
            file.transferTo(destFile);

            // 更新用户头像URL
            String portraitUrl = request.getContextPath() + "/upload/portrait/" + fileName;

            // 更新数据库
            userService.updatePortrait(userId, portraitUrl);

            Map<String, String> data = new HashMap<>();
            data.put("portraitUrl", portraitUrl);
            return Result.success("上传成功", data);
        } catch (IOException e) {
            logger.error("头像上传失败", e);
            return Result.fail("上传失败：" + e.getMessage());
        }
    }

    /**
     * 检查用户名是否存在
     */
    @GetMapping("/checkUsername")
    @ResponseBody
    public Result<Boolean> checkUsername(@RequestParam("username") String username) {
        boolean exists = userService.isUsernameExists(username);
        return Result.success(exists);
    }

    /**
     * 跳转到找回密码页面
     */
    @GetMapping("/findPassword")
    public String toFindPassword() {
        return "find_password";
    }

    /**
     * 发送找回密码验证码
     */
    @PostMapping("/findPassword")
    public String findPassword(@RequestParam("email") String email, Model model) {
        Result<Integer> result = userService.generateResetCode(email);
        model.addAttribute("email", email);

        if (result.isSuccess()) {
            model.addAttribute("code", result.getData());
            return "reset_pwd";
        } else {
            model.addAttribute("errorMsg", result.getMessage());
            return "find_password";
        }
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("code") Integer code,
                               @RequestParam("password") String password,
                               Model model) {
        Result<Boolean> result = userService.resetPassword(code, password);
        if (result.isSuccess()) {
            return "redirect:/user/login";
        } else {
            model.addAttribute("errorMsg", result.getMessage());
            return "reset_pwd";
        }
    }
}
