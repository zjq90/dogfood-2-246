package com.weibo.controller;

import com.weibo.common.Result;
import com.weibo.model.User;
import com.weibo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam(value = "remember", required = false) String remember,
                              @RequestParam(value = "auto", required = false) String auto,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        logger.info("API用户登录请求，用户名：{}", username);
        Result<User> result = userService.login(username, password);
        if (result.isSuccess()) {
            User user = result.getData();
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);
            session.setAttribute("uname", user.getUsername());
            session.setAttribute("userId", user.getUserId());
            
            Cookie usernameCookie = new Cookie("uname", username);
            usernameCookie.setMaxAge(7 * 24 * 60 * 60);
            usernameCookie.setPath("/");
            response.addCookie(usernameCookie);
            
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
            
            if (auto != null) {
                Cookie autoCookie = new Cookie("auto", "auto");
                autoCookie.setMaxAge(7 * 24 * 60 * 60);
                autoCookie.setPath("/");
                response.addCookie(autoCookie);
            }
            logger.info("API用户登录成功，用户名：{}", username);
        }
        return result;
    }

    @PostMapping("/register")
    public Result<User> register(@RequestParam("username") String username,
                                 @RequestParam("password") String password) {
        logger.info("API用户注册请求，用户名：{}", username);
        Result<User> result = userService.register(username, password);
        if (result.isSuccess()) {
            logger.info("API用户注册成功，用户名：{}", username);
        }
        return result;
    }

    @GetMapping("/checkUsername")
    public Result<Boolean> checkUsername(@RequestParam("username") String username) {
        boolean exists = userService.isUsernameExists(username);
        return Result.success(exists);
    }
}
