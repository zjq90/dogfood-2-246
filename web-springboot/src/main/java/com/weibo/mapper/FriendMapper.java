package com.weibo.mapper;

import com.weibo.model.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FriendMapper {

    int insert(Friend friend);

    int updateStatus(@Param("fromUserId") Integer fromUserId, @Param("toUserId") Integer toUserId, @Param("status") Integer status);

    int delete(@Param("fromUserId") Integer fromUserId, @Param("toUserId") Integer toUserId);

    Friend selectRelation(@Param("fromUserId") Integer fromUserId, @Param("toUserId") Integer toUserId);

    List<Friend> selectFollowing(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    int countFollowing(@Param("userId") Integer userId);

    List<Friend> selectFollower(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    int countFollower(@Param("userId") Integer userId);

    List<Friend> selectFriends(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    int countFriends(@Param("userId") Integer userId);

    List<Friend> selectBlacklist(@Param("userId") Integer userId);

    int checkFollowing(@Param("fromUserId") Integer fromUserId, @Param("toUserId") Integer toUserId);
}
