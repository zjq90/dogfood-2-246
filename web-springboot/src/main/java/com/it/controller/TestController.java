package com.it.controller;

import com.it.common.Result;
import com.it.model.User;
import com.it.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Resource
    private UserService userService;

    @GetMapping("/create-test-user")
    public Result<User> createTestUser() {
        log.info("create test user");
        // 先检查用户是否已存在
        User existing = userService.getUserByUsername("testuser");
        if (existing != null) {
            existing.setPassword(null);
            return Result.success("Test user already exists", existing);
        }
        return userService.register("testuser", "123456");
    }

    @GetMapping("/users")
    public Result<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/health")
    public Result<String> health() {
        return Result.success("System is healthy", "OK");
    }
}
