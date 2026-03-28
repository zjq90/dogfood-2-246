package com.weibo.service.impl;

import com.weibo.dto.Result;
import com.weibo.mapper.UserMapper;
import com.weibo.model.User;
import com.weibo.service.UserService;
import com.weibo.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户服务实现类
 * 实现用户相关的业务逻辑
 *
 * @author weibo Team
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 密码重置码缓存（实际项目中应使用Redis）
     */
    private static final ConcurrentHashMap<Integer, Integer> resetCodeCache = new ConcurrentHashMap<>();

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<User> login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return Result.error("用户名或密码不能为空");
        }

        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }

        String encryptedPassword = MD5Util.encrypt(password);
        if (!encryptedPassword.equals(user.getPassword())) {
            return Result.error("密码错误");
        }

        if (user.getStatus() != null && user.getStatus() == 1) {
            return Result.error("账号已被封禁");
        }

        logger.info("用户登录成功: {}", username);
        return Result.success("登录成功", user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<User> register(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return Result.error("用户名或密码不能为空");
        }

        if (username.length() < 3 || username.length() > 20) {
            return Result.error("用户名长度应在3-20个字符之间");
        }

        if (password.length() < 6 || password.length() > 20) {
            return Result.error("密码长度应在6-20个字符之间");
        }

        if (isUsernameExists(username)) {
            return Result.error("用户名已存在");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Util.encrypt(password));
        user.setStatus(0);

        int result = userMapper.insert(user);
        if (result > 0) {
            logger.info("用户注册成功: {}", username);
            return Result.success("注册成功", user);
        }

        return Result.error("注册失败");
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
        if (userId == null) {
            return null;
        }
        return userMapper.selectByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updatePersonage(User user) {
        if (user == null || user.getUserId() == null) {
            return Result.error("用户信息不能为空");
        }

        int result = userMapper.updatePersonage(user);
        if (result > 0) {
            logger.info("更新用户个人信息成功: {}", user.getUserId());
            return Result.success("更新成功", true);
        }

        return Result.error("更新失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updatePortrait(Integer userId, String portrait) {
        if (userId == null || !StringUtils.hasText(portrait)) {
            return Result.error("参数不能为空");
        }

        int result = userMapper.updatePortrait(userId, portrait);
        if (result > 0) {
            logger.info("更新用户头像成功: {}", userId);
            return Result.success("头像更新成功", true);
        }

        return Result.error("头像更新失败");
    }

    @Override
    public Result<Integer> generateResetCode(String username) {
        User user = getUserByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }

        int code = new Random().nextInt(900000) + 100000;
        resetCodeCache.put(code, user.getUserId());

        logger.info("生成密码重置码: {} 用户: {}", code, username);
        return Result.success("验证码已发送", code);
    }

    @Override
    public Result<User> verifyResetCode(Integer code) {
        Integer userId = resetCodeCache.get(code);
        if (userId == null) {
            return Result.error("验证码无效或已过期");
        }

        User user = getUserByUserId(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        return Result.success("验证成功", user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> resetPassword(Integer code, String newPassword) {
        if (!StringUtils.hasText(newPassword) || newPassword.length() < 6) {
            return Result.error("密码长度不能少于6位");
        }

        Integer userId = resetCodeCache.get(code);
        if (userId == null) {
            return Result.error("验证码无效或已过期");
        }

        int result = userMapper.updatePassword(userId, MD5Util.encrypt(newPassword));
        if (result > 0) {
            resetCodeCache.remove(code);
            logger.info("密码重置成功: {}", userId);
            return Result.success("密码重置成功", true);
        }

        return Result.error("密码重置失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> changePassword(Integer userId, String oldPassword, String newPassword) {
        if (userId == null || !StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword)) {
            return Result.error("参数不能为空");
        }

        User user = getUserByUserId(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        if (!MD5Util.encrypt(oldPassword).equals(user.getPassword())) {
            return Result.error("原密码错误");
        }

        if (newPassword.length() < 6) {
            return Result.error("新密码长度不能少于6位");
        }

        int result = userMapper.updatePassword(userId, MD5Util.encrypt(newPassword));
        if (result > 0) {
            logger.info("密码修改成功: {}", userId);
            return Result.success("密码修改成功", true);
        }

        return Result.error("密码修改失败");
    }

    @Override
    public Result<List<User>> searchUsers(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return Result.success("搜索成功", userMapper.selectAll());
        }
        return Result.success("搜索成功", userMapper.searchByKeyword(keyword));
    }

    @Override
    public Result<List<User>> getAllUsers() {
        return Result.success("获取成功", userMapper.selectAll());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updateStatus(Integer userId, Integer status) {
        if (userId == null || status == null) {
            return Result.error("参数不能为空");
        }

        int result = userMapper.updateStatus(userId, status);
        if (result > 0) {
            logger.info("更新用户状态成功: {} -> {}", userId, status);
            return Result.success("操作成功", true);
        }

        return Result.error("操作失败");
    }

    @Override
    public Result<Boolean> reportUser(Integer userId) {
        logger.info("举报用户: {}", userId);
        return Result.success("举报已提交", true);
    }
}
