package com.weibo.service.impl;

import com.weibo.common.Result;
import com.weibo.mapper.UserMapper;
import com.weibo.model.User;
import com.weibo.service.UserService;
import com.weibo.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<User> login(String username, String password) {
        try {
            String md5Password = MD5Util.md5(password);
            User user = userMapper.selectForLogin(username, md5Password);
            if (user == null) {
                return Result.error(401, "用户名或密码错误");
            }
            if (user.getStatus() != null && user.getStatus() == 1) {
                if (user.getTitleTime() != null && user.getTitleTime().after(new Date())) {
                    return Result.error(403, "账号已被封禁，解封时间：" + user.getTitleTime());
                }
            }
            return Result.success(user);
        } catch (Exception e) {
            logger.error("登录异常", e);
            return Result.error("登录异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<User> register(String username, String password) {
        try {
            if (isUsernameExists(username)) {
                return Result.error("用户名已存在");
            }
            String md5Password = MD5Util.md5(password);
            User user = new User();
            user.setUsername(username);
            user.setPassword(md5Password);
            user.setStatus(0);
            user.setReported(0);
            user.setNickname(username);
            user.setTime(new Date());
            
            int result = userMapper.insertUser(user);
            if (result > 0) {
                return Result.success(user);
            }
            return Result.error("注册失败");
        } catch (Exception e) {
            logger.error("注册异常", e);
            return Result.error("注册异常：" + e.getMessage());
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
    @Transactional
    public Result<Boolean> updatePersonage(User user) {
        try {
            int result = userMapper.updatePersonage(user);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("更新失败");
        } catch (Exception e) {
            logger.error("更新个人信息异常", e);
            return Result.error("更新异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> updatePortrait(Integer userId, String portrait) {
        try {
            int result = userMapper.updatePortrait(userId, portrait);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("更新头像失败");
        } catch (Exception e) {
            logger.error("更新头像异常", e);
            return Result.error("更新头像异常：" + e.getMessage());
        }
    }

    @Override
    public Result<Integer> generateResetCode(String username) {
        return Result.success(123456);
    }

    @Override
    public Result<User> verifyResetCode(Integer code) {
        User user = userMapper.selectByCode(code);
        if (user != null) {
            return Result.success(user);
        }
        return Result.error("无效的验证码");
    }

    @Override
    @Transactional
    public Result<Boolean> resetPassword(Integer code, String newPassword) {
        try {
            User user = userMapper.selectByCode(code);
            if (user == null) {
                return Result.error("无效的验证码");
            }
            String md5Password = MD5Util.md5(newPassword);
            int result = userMapper.updatePassword(user.getUserId(), md5Password);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("重置密码失败");
        } catch (Exception e) {
            logger.error("重置密码异常", e);
            return Result.error("重置密码异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> changePassword(Integer userId, String oldPassword, String newPassword) {
        try {
            User user = userMapper.selectByUserId(userId);
            if (user == null) {
                return Result.error("用户不存在");
            }
            String oldMd5Password = MD5Util.md5(oldPassword);
            if (!oldMd5Password.equals(user.getPassword())) {
                return Result.error("原密码错误");
            }
            String newMd5Password = MD5Util.md5(newPassword);
            int result = userMapper.updatePassword(userId, newMd5Password);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("修改密码失败");
        } catch (Exception e) {
            logger.error("修改密码异常", e);
            return Result.error("修改密码异常：" + e.getMessage());
        }
    }

    @Override
    public Result<List<User>> searchUsers(String keyword) {
        try {
            List<User> users = userMapper.searchUsers(keyword);
            return Result.success(users);
        } catch (Exception e) {
            logger.error("搜索用户异常", e);
            return Result.error("搜索异常：" + e.getMessage());
        }
    }

    @Override
    public Result<List<User>> getAllUsers() {
        try {
            List<User> users = userMapper.selectAllUsers();
            return Result.success(users);
        } catch (Exception e) {
            logger.error("获取用户列表异常", e);
            return Result.error("获取用户列表异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> updateStatus(Integer userId, Integer status) {
        try {
            int result = userMapper.updateStatus(userId, status);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("更新状态失败");
        } catch (Exception e) {
            logger.error("更新用户状态异常", e);
            return Result.error("更新状态异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> reportUser(Integer userId) {
        try {
            int result = userMapper.updateReported(userId, 1);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("举报失败");
        } catch (Exception e) {
            logger.error("举报用户异常", e);
            return Result.error("举报异常：" + e.getMessage());
        }
    }
}
