package com.it.mapper;

import com.it.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户Mapper接口
 * 
 * @author weibo Team
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户对象
     */
    User selectByUsername(@Param("username") String username);

    /**
     * 根据用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象
     */
    User selectByUserId(@Param("userId") Integer userId);

    /**
     * 用户登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    User selectForLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 新增用户（注册）
     * 
     * @param user 用户对象
     * @return 影响行数
     */
    int insertUser(User user);

    /**
     * 更新用户信息
     * 
     * @param user 用户对象
     * @return 影响行数
     */
    int updateUser(User user);

    /**
     * 更新用户头像
     * 
     * @param userId 用户ID
     * @param portrait 头像路径
     * @return 影响行数
     */
    int updatePortrait(@Param("userId") Integer userId, @Param("portrait") String portrait);

    /**
     * 更新用户个人信息
     * 
     * @param user 用户对象（包含签名、自我介绍、昵称、地址）
     * @return 影响行数
     */
    int updatePersonage(User user);

    /**
     * 更新找回密码凭证和过期时间
     * 
     * @param user 用户对象
     * @return 影响行数
     */
    int updateCodeAndOutTime(User user);

    /**
     * 根据找回密码凭证查询用户
     * 
     * @param code 凭证码
     * @return 用户对象
     */
    User selectByCode(@Param("code") Integer code);

    /**
     * 更新用户密码
     * 
     * @param userId 用户ID
     * @param password 新密码
     * @return 影响行数
     */
    int updatePassword(@Param("userId") Integer userId, @Param("password") String password);

    /**
     * 查询所有用户（管理员功能）
     * 
     * @return 用户列表
     */
    List<User> selectAllUsers();

    /**
     * 根据关键词搜索用户
     * 
     * @param keyword 关键词（用户名/昵称）
     * @return 用户列表
     */
    List<User> searchUsers(@Param("keyword") String keyword);

    /**
     * 更新用户状态（封禁/解封）
     * 
     * @param userId 用户ID
     * @param status 状态
     * @return 影响行数
     */
    int updateStatus(@Param("userId") Integer userId, @Param("status") Integer status);

    /**
     * 更新用户举报状态
     * 
     * @param userId 用户ID
     * @param reported 举报状态
     * @return 影响行数
     */
    int updateReported(@Param("userId") Integer userId, @Param("reported") Integer reported);
}
