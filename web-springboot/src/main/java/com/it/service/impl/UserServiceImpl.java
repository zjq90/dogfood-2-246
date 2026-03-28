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

/**
 * 用户服务实现类
 * 
 * @author weibo Team
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码（未加密）
     * @return 登录结果
     */
    @Override
    public Result<User> login(String username, String password) {
        log.info("user login, username: {}", username);
        User user = userMapper.selectForLogin(username, MD5Util.encrypt(password));
        if (user == null) {
            return Result.error("用户名或密码错误");
        }
        user.setPassword(null);
        return Result.success("登录成功", user);
    }

    /**
     * 用户注册
     * 
     * @param username 用户名
     * @param password 密码（未加密）
     * @return 注册结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<User> register(String username, String password) {
        log.info("user register, username: {}", username);
        User existingUser = userMapper.selectByUsername(username);
        if (existingUser != null) {
            return Result.error("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Util.encrypt(password));
        user.setStatus(0);
        user.setReported(0);
        user.setNickname(username);
        user.setPortrait("/static/image/default.png");
        user.setTime(new Date());
        int result = userMapper.insertUser(user);
        if (result > 0) {
            user.setPassword(null);
            return Result.success("注册成功", user);
        }
        return Result.error("注册失败");
    }

    /**
     * 检查用户名是否已存在
     * 
     * @param username 用户名
     * @return true-存在，false-不存在
     */
    @Override
    public boolean isUsernameExists(String username) {
        User user = userMapper.selectByUsername(username);
        return user != null;
    }

    /**
     * 根据用户名获取用户信息
     * 
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户对象
     */
    @Override
    public User getUserByUserId(Integer userId) {
        return userMapper.selectByUserId(userId);
    }

    /**
     * 更新用户个人信息
     * 
     * @param user 用户对象（包含需要更新的字段）
     * @return 更新结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updatePersonage(User user) {
        log.info("update user info, userId: {}", user.getUserId());
        int result = userMapper.updatePersonage(user);
        return result > 0 ? Result.success("更新成功", true) : Result.error("更新失败");
    }

    /**
     * 更新用户头像
     * 
     * @param userId 用户ID
     * @param portrait 头像路径
     * @return 更新结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updatePortrait(Integer userId, String portrait) {
        log.info("update user portrait, userId: {}", userId);
        int result = userMapper.updatePortrait(userId, portrait);
        return result > 0 ? Result.success("更新成功", true) : Result.error("更新失败");
    }

    /**
     * 生成找回密码凭证
     * 
     * @param username 用户名（邮箱）
     * @return 生成的凭证码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Integer> generateResetCode(String username) {
        log.info("generate reset code, username: {}", username);
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        user.setCode(code);
        user.setOutTime(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
        int result = userMapper.updateCodeAndOutTime(user);
        return result > 0 ? Result.success("验证码已发送", code) : Result.error("生成验证码失败");
    }

    /**
     * 验证找回密码凭证
     * 
     * @param code 凭证码
     * @return 用户对象
     */
    @Override
    public Result<User> verifyResetCode(Integer code) {
        log.info("verify reset code, code: {}", code);
        User user = userMapper.selectByCode(code);
        if (user == null) {
            return Result.error("验证码无效");
        }
        if (user.getOutTime() != null && user.getOutTime().before(new Date())) {
            return Result.error("验证码已过期");
        }
        user.setPassword(null);
        return Result.success("验证成功", user);
    }

    /**
     * 重置密码
     * 
     * @param code 凭证码
     * @param newPassword 新密码
     * @return 重置结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> resetPassword(Integer code, String newPassword) {
        log.info("reset password, code: {}", code);
        User user = userMapper.selectByCode(code);
        if (user == null) {
            return Result.error("验证码无效");
        }
        if (user.getOutTime() != null && user.getOutTime().before(new Date())) {
            return Result.error("验证码已过期");
        }
        int result = userMapper.updatePassword(user.getUserId(), MD5Util.encrypt(newPassword));
        return result > 0 ? Result.success("密码重置成功", true) : Result.error("密码重置失败");
    }

    /**
     * 修改密码
     * 
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> changePassword(Integer userId, String oldPassword, String newPassword) {
        log.info("change password, userId: {}", userId);
        User user = userMapper.selectByUserId(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        if (!MD5Util.verify(oldPassword, user.getPassword())) {
            return Result.error("旧密码不正确");
        }
        int result = userMapper.updatePassword(userId, MD5Util.encrypt(newPassword));
        return result > 0 ? Result.success("密码修改成功", true) : Result.error("密码修改失败");
    }

    /**
     * 搜索用户
     * 
     * @param keyword 关键词
     * @return 用户列表
     */
    @Override
    public Result<List<User>> searchUsers(String keyword) {
        log.info("search users, keyword: {}", keyword);
        List<User> users = userMapper.searchUsers(keyword);
        users.forEach(u -> u.setPassword(null));
        return Result.success("搜索成功", users);
    }

    /**
     * 获取所有用户（管理员功能）
     * 
     * @return 用户列表
     */
    @Override
    public Result<List<User>> getAllUsers() {
        log.info("get all users");
        List<User> users = userMapper.selectAllUsers();
        users.forEach(u -> u.setPassword(null));
        return Result.success("获取成功", users);
    }

    /**
     * 更新用户状态（封禁/解封）
     * 
     * @param userId 用户ID
     * @param status 状态
     * @return 更新结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updateStatus(Integer userId, Integer status) {
        log.info("update user status, userId: {}, status: {}", userId, status);
        int result = userMapper.updateStatus(userId, status);
        return result > 0 ? Result.success("更新成功", true) : Result.error("更新失败");
    }

    /**
     * 举报用户
     * 
     * @param userId 用户ID
     * @return 举报结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> reportUser(Integer userId) {
        log.info("report user, userId: {}", userId);
        int result = userMapper.updateReported(userId, 1);
        return result > 0 ? Result.success("举报成功", true) : Result.error("举报失败");
    }
}
