package com.it.service.impl;

import com.it.common.Result;
import com.it.mapper.UserMapper;
import com.it.model.User;
import com.it.service.UserService;
import com.it.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Result<User> login(String username, String password) {
        log.info("user login, username: {}", username);
        User user = userMapper.selectForLogin(username, MD5Util.encrypt(password));
        if (user == null) {
            return Result.error("Invalid username or password");
        }
        user.setPassword(null);
        return Result.success("Login success", user);
    }

    @Override
    public Result<User> register(String username, String password) {
        log.info("user register, username: {}", username);
        User existingUser = userMapper.selectByUsername(username);
        if (existingUser != null) {
            return Result.error("Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Util.encrypt(password));
        user.setStatus(0);
        user.setReported(0);
        user.setNickname(username);
        user.setPortrait("/static/image/default.png");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        int result = userMapper.insertUser(user);
        if (result > 0) {
            user.setPassword(null);
            return Result.success("Register success", user);
        }
        return Result.error("Register failed");
    }

    @Override
    public boolean isUsernameExists(String username) {
        User user = userMapper.selectByUsername(username);
        return user != null;
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User getUserByUserId(Integer userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updatePersonage(User user) {
        log.info("update user info, userId: {}", user.getUserId());
        user.setUpdateTime(new Date());
        int result = userMapper.updatePersonage(user);
        return result > 0 ? Result.success("Update success", true) : Result.error("Update failed");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updatePortrait(Integer userId, String portrait) {
        log.info("update user portrait, userId: {}", userId);
        int result = userMapper.updatePortrait(userId, portrait);
        return result > 0 ? Result.success("Update success", true) : Result.error("Update failed");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Integer> generateResetCode(String username) {
        log.info("generate reset code, username: {}", username);
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return Result.error("User not exists");
        }
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        user.setCode(code);
        user.setOutTime(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
        int result = userMapper.updateCodeAndOutTime(user);
        return result > 0 ? Result.success("Code generated", code) : Result.error("Generate code failed");
    }

    @Override
    public Result<User> verifyResetCode(Integer code) {
        log.info("verify reset code, code: {}", code);
        User user = userMapper.selectByCode(code);
        if (user == null) {
            return Result.error("Invalid code");
        }
        if (user.getOutTime() != null && user.getOutTime().before(new Date())) {
            return Result.error("Code expired");
        }
        user.setPassword(null);
        return Result.success("Valid code", user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> resetPassword(Integer code, String newPassword) {
        log.info("reset password, code: {}", code);
        User user = userMapper.selectByCode(code);
        if (user == null) {
            return Result.error("Invalid code");
        }
        if (user.getOutTime() != null && user.getOutTime().before(new Date())) {
            return Result.error("Code expired");
        }
        int result = userMapper.updatePassword(user.getUserId(), MD5Util.encrypt(newPassword));
        return result > 0 ? Result.success("Password reset", true) : Result.error("Reset failed");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> changePassword(Integer userId, String oldPassword, String newPassword) {
        log.info("change password, userId: {}", userId);
        User user = userMapper.selectByUserId(userId);
        if (user == null) {
            return Result.error("User not exists");
        }
        if (!MD5Util.verify(oldPassword, user.getPassword())) {
            return Result.error("Old password incorrect");
        }
        int result = userMapper.updatePassword(userId, MD5Util.encrypt(newPassword));
        return result > 0 ? Result.success("Password changed", true) : Result.error("Change failed");
    }

    @Override
    public Result<List<User>> searchUsers(String keyword) {
        log.info("search users, keyword: {}", keyword);
        List<User> users = userMapper.searchUsers(keyword);
        users.forEach(u -> u.setPassword(null));
        return Result.success("Search success", users);
    }

    @Override
    public Result<List<User>> getAllUsers() {
        log.info("get all users");
        List<User> users = userMapper.selectAllUsers();
        users.forEach(u -> u.setPassword(null));
        return Result.success("Get success", users);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updateStatus(Integer userId, Integer status) {
        log.info("update user status, userId: {}, status: {}", userId, status);
        int result = userMapper.updateStatus(userId, status);
        return result > 0 ? Result.success("Update success", true) : Result.error("Update failed");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> reportUser(Integer userId) {
        log.info("report user, userId: {}", userId);
        int result = userMapper.updateReported(userId, 1);
        return result > 0 ? Result.success("Report success", true) : Result.error("Report failed");
    }
}
