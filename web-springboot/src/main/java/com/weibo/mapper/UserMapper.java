package com.weibo.mapper;

import com.weibo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户Mapper接口
 * 提供用户数据的增删改查操作
 * 
 * @author weibo Team
 */
@Mapper
public interface UserMapper {

    /**
     * 用户登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    User login(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象
     */
    User selectByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户对象
     */
    User selectByUsername(@Param("username") String username);

    /**
     * 新增用户（注册）
     * 
     * @param user 用户对象
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 更新用户信息
     * 
     * @param user 用户对象
     * @return 影响行数
     */
    int update(User user);

    /**
     * 更新用户头像
     * 
     * @param userId 用户ID
     * @param portrait 头像路径
     * @return 影响行数
     */
    int updatePortrait(@Param("userId") Integer userId, @Param("portrait") String portrait);

    /**
     * 更新用户密码
     * 
     * @param userId 用户ID
     * @param password 新密码
     * @return 影响行数
     */
    int updatePassword(@Param("userId") Integer userId, @Param("password") String password);

    /**
     * 分页查询用户列表
     * 
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 用户列表
     */
    List<User> selectByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询用户总数
     * 
     * @return 用户总数
     */
    int selectCount();

    /**
     * 搜索用户（分页）
     * 
     * @param searchContent 搜索内容
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 用户列表
     */
    List<User> selectSearchByPage(@Param("searchContent") String searchContent,
                                  @Param("offset") Integer offset,
                                  @Param("pageSize") Integer pageSize);

    /**
     * 搜索用户总数
     * 
     * @param searchContent 搜索内容
     * @return 用户总数
     */
    int selectSearchCount(@Param("searchContent") String searchContent);

    /**
     * 检查用户名是否存在
     * 
     * @param username 用户名
     * @return 存在数量
     */
    int checkUsername(@Param("username") String username);

    /**
     * 根据找回密码凭证查询用户
     * 
     * @param code 凭证码
     * @return 用户对象
     */
    User selectByCode(@Param("code") Integer code);

    /**
     * 更新找回密码凭证和过期时间
     * 
     * @param user 用户对象
     * @return 影响行数
     */
    int updateCodeAndOutTime(User user);

    /**
     * 更新用户个人信息
     * 
     * @param user 用户对象
     * @return 影响行数
     */
    int updatePersonage(User user);

    /**
     * 查询所有用户
     * 
     * @return 用户列表
     */
    List<User> selectAllUsers();

    /**
     * 搜索用户
     * 
     * @param keyword 关键词
     * @return 用户列表
     */
    List<User> searchUsers(@Param("keyword") String keyword);

    /**
     * 更新用户状态
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

    /**
     * 新增用户（注册）- 别名方法
     * 
     * @param user 用户对象
     * @return 影响行数
     */
    default int insertUser(User user) {
        return insert(user);
    }

    /**
     * 用户登录验证 - 别名方法
     * 
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    default User selectForLogin(String username, String password) {
        return login(username, password);
    }
}
