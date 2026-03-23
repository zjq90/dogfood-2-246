package com.weibo.controller;

import com.weibo.common.Result;
import com.weibo.dto.PageResult;
import com.weibo.model.Article;
import com.weibo.model.User;
import com.weibo.service.ArticleService;
import com.weibo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String toLogin(HttpServletRequest request, Model model) {
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
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

    @PostMapping("/login")
    public String login(@RequestParam("uname") String username,
                        @RequestParam("upwd") String password,
                        @RequestParam(value = "remember", required = false) String remember,
                        @RequestParam(value = "auto", required = false) String auto,
                        HttpServletRequest request,
                        javax.servlet.http.HttpServletResponse response,
                        Model model) {
        logger.info("用户登录请求，用户名：{}", username);
        Result<User> result = userService.login(username, password);
        if (result.isSuccess()) {
            User user = result.getData();
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);
            session.setAttribute("uname", user.getUsername());
            session.setAttribute("userId", user.getUserId());
            javax.servlet.http.Cookie usernameCookie = new javax.servlet.http.Cookie("uname", username);
            usernameCookie.setMaxAge(7 * 24 * 60 * 60);
            usernameCookie.setPath("/");
            response.addCookie(usernameCookie);
            if (remember != null) {
                javax.servlet.http.Cookie passwordCookie = new javax.servlet.http.Cookie("upwd", password);
                passwordCookie.setMaxAge(7 * 24 * 60 * 60);
                passwordCookie.setPath("/");
                response.addCookie(passwordCookie);
            } else {
                javax.servlet.http.Cookie passwordCookie = new javax.servlet.http.Cookie("upwd", "");
                passwordCookie.setMaxAge(0);
                passwordCookie.setPath("/");
                response.addCookie(passwordCookie);
            }
            if (auto != null) {
                javax.servlet.http.Cookie autoCookie = new javax.servlet.http.Cookie("auto", "auto");
                autoCookie.setMaxAge(7 * 24 * 60 * 60);
                autoCookie.setPath("/");
                response.addCookie(autoCookie);
            }
            logger.info("用户登录成功，用户名：{}", username);
            return "redirect:/page/home";
        } else {
            model.addAttribute("errorMsg", result.getMsg());
            return "login";
        }
    }

    @GetMapping("/register")
    public String toRegister() {
        return "register";
    }

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
            model.addAttribute("errorMsg", result.getMsg());
            model.addAttribute("username", username);
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        session.removeAttribute("uname");
        session.removeAttribute("userId");
        session.invalidate();
        javax.servlet.http.Cookie autoCookie = new javax.servlet.http.Cookie("auto", "");
        autoCookie.setMaxAge(0);
        autoCookie.setPath("/");
        response.addCookie(autoCookie);
        logger.info("用户退出登录");
        return "redirect:/user/login";
    }

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

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute User user, HttpServletRequest request, Model model) {
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

    @GetMapping("/checkUsername")
    @ResponseBody
    public Result<Boolean> checkUsername(@RequestParam("username") String username) {
        boolean exists = userService.isUsernameExists(username);
        return Result.success(exists);
    }
}
