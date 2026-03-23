package com.weibo.service.impl;

import com.weibo.dto.Result;
import com.weibo.mapper.UserMapper;
import com.weibo.model.User;
import com.weibo.service.UserService;
import com.weibo.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<User> login(String username, String password) {
        log.info("用户登录: {}", username);
        
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return Result.error("用户名或密码不能为空");
        }

        String encryptedPassword = MD5Util.encrypt(password);
        User user = userMapper.selectForLogin(username, encryptedPassword);

        if (user == null) {
            return Result.error("用户名或密码错误");
        }

        // 检查是否被封号
        if (user.getTitleTime() != null && user.getTitleTime().after(new Date())) {
            return Result.error("账号已被封禁，解封时间：" + user.getTitleTime());
        }

        return Result.success("登录成功", user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<User> register(String username, String password) {
        log.info("用户注册: {}", username);
        
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return Result.error("用户名或密码不能为空");
        }

        // 检查用户名是否已存在
        if (isUsernameExists(username)) {
            return Result.error("用户名已存在");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Util.encrypt(password));
        user.setStatus(0);
        user.setReported(0);
        user.setPortrait("/static/image/default.png");
        user.setNickname("用户" + System.currentTimeMillis() % 10000);
        user.setTime(new Date());

        int result = userMapper.insertUser(user);
        if (result > 0) {
            return Result.success("注册成功", user);
        } else {
            return Result.error("注册失败");
        }
    }

    @Override
    public boolean isUsernameExists(String username) {
        return userMapper.selectByUsername(username) != null;
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
        log.info("更新用户信息: {}", user.getUserId());
        
        int result = userMapper.updateUser(user);
        if (result > 0) {
            return Result.success("更新成功", true);
        } else {
            return Result.error("更新失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updatePortrait(Integer userId, String portrait) {
        log.info("更新用户头像: {}", userId);
        
        int result = userMapper.updatePortrait(userId, portrait);
        if (result > 0) {
            return Result.success("更新成功", true);
        } else {
            return Result.error("更新失败");
        }
    }

    @Override
    public Result<Integer> generateResetCode(String username) {
        log.info("生成找回密码凭证: {}", username);
        
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 生成6位随机码
        int code = new Random().nextInt(900000) + 100000;
        
        // 设置验证码和过期时间（30分钟后过期）
        userMapper.updateResetCode(username, code, new Date(System.currentTimeMillis() + 30 * 60 * 1000));
        
        return Result.success("验证码已发送", code);
    }

    @Override
    public Result<User> verifyResetCode(Integer code) {
        log.info("验证找回密码凭证: {}", code);
        
        User user = userMapper.selectByResetCode(code);
        if (user == null) {
            return Result.error("验证码无效");
        }
        
        if (user.getOutTime() != null && user.getOutTime().before(new Date())) {
            return Result.error("验证码已过期");
        }
        
        return Result.success("验证成功", user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> resetPassword(Integer code, String newPassword) {
        log.info("重置密码: {}", code);
        
        User user = userMapper.selectByResetCode(code);
        if (user == null) {
            return Result.error("验证码无效");
        }
        
        if (user.getOutTime() != null && user.getOutTime().before(new Date())) {
            return Result.error("验证码已过期");
        }

        int result = userMapper.updatePassword(user.getUserId(), MD5Util.encrypt(newPassword));
        if (result > 0) {
            // 清除验证码
            userMapper.updateResetCode(user.getUsername(), null, null);
            return Result.success("密码重置成功", true);
        } else {
            return Result.error("密码重置失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> changePassword(Integer userId, String oldPassword, String newPassword) {
        log.info("修改密码: {}", userId);
        
        User user = userMapper.selectByUserId(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        if (!user.getPassword().equals(MD5Util.encrypt(oldPassword))) {
            return Result.error("原密码错误");
        }

        int result = userMapper.updatePassword(userId, MD5Util.encrypt(newPassword));
        if (result > 0) {
            return Result.success("密码修改成功", true);
        } else {
            return Result.error("密码修改失败");
        }
    }

    @Override
    public Result<List<User>> searchUsers(String keyword, Integer pageNum, Integer pageSize) {
        log.info("搜索用户: {}", keyword);
        
        int offset = (pageNum - 1) * pageSize;
        List<User> users = userMapper.searchUsers(keyword, offset, pageSize);
        return Result.success(users);
    }
}
