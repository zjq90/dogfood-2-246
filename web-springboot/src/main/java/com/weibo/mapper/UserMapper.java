package com.weibo.mapper;

import com.weibo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {

    User selectByUsername(@Param("username") String username);

    User selectByUserId(@Param("userId") Integer userId);

    User selectForLogin(@Param("username") String username, @Param("password") String password);

    int insertUser(User user);

    int updateUser(User user);

    int updatePortrait(@Param("userId") Integer userId, @Param("portrait") String portrait);

    int updatePersonage(User user);

    int updatePassword(@Param("userId") Integer userId, @Param("password") String password);

    User selectByCode(@Param("code") Integer code);

    List<User> selectAllUsers();

    List<User> searchUsers(@Param("keyword") String keyword);

    int updateStatus(@Param("userId") Integer userId, @Param("status") Integer status);

    int updateReported(@Param("userId") Integer userId, @Param("reported") Integer reported);
}
