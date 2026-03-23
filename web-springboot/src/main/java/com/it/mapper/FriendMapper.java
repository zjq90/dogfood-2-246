package com.it.mapper;

import com.it.model.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FriendMapper {

    List<Friend> selectFollowingList(@Param("userId") Integer userId,
                                     @Param("offset") Integer offset,
                                     @Param("pageSize") Integer pageSize);

    List<Friend> selectFollowerList(@Param("userId") Integer userId,
                                    @Param("offset") Integer offset,
                                    @Param("pageSize") Integer pageSize);

    List<Friend> selectFriendList(@Param("userId") Integer userId,
                                  @Param("offset") Integer offset,
                                  @Param("pageSize") Integer pageSize);

    List<Friend> selectBlacklist(@Param("userId") Integer userId);

    Friend selectRelation(@Param("fromUserId") Integer fromUserId,
                          @Param("toUserId") Integer toUserId);

    int insertFriend(Friend friend);
    
    int insertFollow(Friend friend);

    int updateFriendStatus(Friend friend);
    
    int updateStatus(@Param("fromUserId") Integer fromUserId,
                     @Param("toUserId") Integer toUserId,
                     @Param("status") Integer status);

    int deleteFriend(@Param("fromUserId") Integer fromUserId,
                     @Param("toUserId") Integer toUserId);
    
    int deleteFollow(@Param("fromUserId") Integer fromUserId,
                     @Param("toUserId") Integer toUserId);

    Integer selectFollowingCount(@Param("userId") Integer userId);

    Integer selectFollowerCount(@Param("userId") Integer userId);

    Integer selectFriendCount(@Param("userId") Integer userId);

    Integer checkIsFollowing(@Param("fromUserId") Integer fromUserId,
                             @Param("toUserId") Integer toUserId);
    
    int selectFollowExists(@Param("fromUserId") Integer fromUserId,
                           @Param("toUserId") Integer toUserId);
}
