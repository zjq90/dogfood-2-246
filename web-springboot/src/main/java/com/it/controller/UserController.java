package com.it.controller;

import com.it.common.Result;
import com.it.model.User;
import com.it.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<User> login(String username, String password, HttpSession session) {
        log.info("user login, username: {}", username);
        Result<User> result = userService.login(username, password);
        if (result.isSuccess()) {
            User user = result.getData();
            session.setAttribute("userInfo", user);
            session.setAttribute("userId", user.getUserId());
        }
        return result;
    }

    @PostMapping("/register")
    public Result<User> register(String username, String password) {
        log.info("user register, username: {}", username);
        return userService.register(username, password);
    }

    @GetMapping("/logout")
    public Result<Void> logout(HttpSession session) {
        log.info("user logout");
        session.removeAttribute("userInfo");
        return Result.success();
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("Not logged in");
        }
        return Result.success(user);
    }

    @GetMapping("/username/{username}")
    public Result<User> getByUsername(@PathVariable String username) {
        log.info("get user by username: {}", username);
        User user = userService.getUserByUsername(username);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    @PostMapping("/update")
    public Result<Boolean> updatePersonage(@RequestBody User user, HttpSession session) {
        User currentUser = (User) session.getAttribute("userInfo");
        if (currentUser == null) {
            return Result.error("Not logged in");
        }
        user.setUserId(currentUser.getUserId());
        return userService.updatePersonage(user);
    }

    @GetMapping("/personage/{userId}")
    public Result<User> getPersonage(@PathVariable Integer userId) {
        log.info("get personage, userId: {}", userId);
        User user = userService.getUserByUserId(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    @GetMapping("/reset-code")
    public Result<Integer> generateResetCode(String username) {
        log.info("generate reset code, username: {}", username);
        return userService.generateResetCode(username);
    }

    @GetMapping("/verify-code")
    public Result<User> verifyResetCode(Integer code) {
        log.info("verify reset code, code: {}", code);
        return userService.verifyResetCode(code);
    }

    @PostMapping("/reset-pwd")
    public Result<Boolean> resetPassword(Integer code, String newPassword) {
        log.info("reset password, code: {}", code);
        return userService.resetPassword(code, newPassword);
    }

    @PostMapping("/change-pwd")
    public Result<Boolean> changePassword(String oldPassword, String newPassword, HttpSession session) {
        User currentUser = (User) session.getAttribute("userInfo");
        if (currentUser == null) {
            return Result.error("Not logged in");
        }
        return userService.changePassword(currentUser.getUserId(), oldPassword, newPassword);
    }

    @GetMapping("/search")
    public Result<java.util.List<User>> searchUsers(String keyword) {
        log.info("search users, keyword: {}", keyword);
        return userService.searchUsers(keyword);
    }

    @GetMapping("/all")
    public Result<java.util.List<User>> getAllUsers() {
        log.info("get all users");
        return userService.getAllUsers();
    }

    @PostMapping("/status")
    public Result<Boolean> updateStatus(Integer userId, Integer status) {
        log.info("update user status, userId: {}, status: {}", userId, status);
        return userService.updateStatus(userId, status);
    }

    @PostMapping("/report")
    public Result<Boolean> reportUser(Integer userId) {
        log.info("report user, userId: {}", userId);
        return userService.reportUser(userId);
    }
}
