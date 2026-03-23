package com.weibo.service;

import com.weibo.dto.Result;
import com.weibo.entity.User;
import java.util.List;

/**
 * 用户服务接口
 * 
 * @author weibo Team
 */
public interface UserService {

    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码（未加密）
     * @return 登录结果
     */
    Result<User> login(String username, String password);

    /**
     * 用户注册
     * 
     * @param username 用户名
     * @param password 密码（未加密）
     * @return 注册结果
     */
    Result<User> register(String username, String password);

    /**
     * 检查用户名是否已存在
     * 
     * @param username 用户名
     * @return true-存在，false-不存在
     */
    boolean isUsernameExists(String username);

    /**
     * 根据用户名获取用户信息
     * 
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByUsername(String username);

    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户对象
     */
    User getUserByUserId(Integer userId);

    /**
     * 更新用户个人信息
     * 
     * @param user 用户对象（包含需要更新的字段）
     * @return 更新结果
     */
    Result<Boolean> updatePersonage(User user);

    /**
     * 更新用户头像
     * 
     * @param userId 用户ID
     * @param portrait 头像路径
     * @return 更新结果
     */
    Result<Boolean> updatePortrait(Integer userId, String portrait);

    /**
     * 生成找回密码凭证
     * 
     * @param username 用户名（邮箱）
     * @return 生成的凭证码
     */
    Result<Integer> generateResetCode(String username);

    /**
     * 验证找回密码凭证
     * 
     * @param code 凭证码
     * @return 用户对象
     */
    Result<User> verifyResetCode(Integer code);

    /**
     * 重置密码
     * 
     * @param code 凭证码
     * @param newPassword 新密码
     * @return 重置结果
     */
    Result<Boolean> resetPassword(Integer code, String newPassword);

    /**
     * 修改密码
     * 
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    Result<Boolean> changePassword(Integer userId, String oldPassword, String newPassword);

    /**
     * 搜索用户
     * 
     * @param keyword 关键词
     * @return 用户列表
     */
    Result<List<User>> searchUsers(String keyword);

    /**
     * 获取所有用户（管理员功能）
     * 
     * @return 用户列表
     */
    Result<List<User>> getAllUsers();

    /**
     * 更新用户状态（封禁/解封）
     * 
     * @param userId 用户ID
     * @param status 状态
     * @return 更新结果
     */
    Result<Boolean> updateStatus(Integer userId, Integer status);

    /**
     * 举报用户
     * 
     * @param userId 用户ID
     * @return 举报结果
     */
    Result<Boolean> reportUser(Integer userId);
}
