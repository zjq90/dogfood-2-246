package com.it.mapper;

import com.it.model.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 好友关系Mapper接口
 * 
 * @author weibo Team
 */
@Mapper
public interface FriendMapper {

    /**
     * 查询用户的关注列表
     * 
     * @param userId 用户ID
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 好友关系列表
     */
    List<Friend> selectFollowingList(@Param("userId") Integer userId,
                                     @Param("offset") Integer offset,
                                     @Param("pageSize") Integer pageSize);

    /**
     * 查询用户的粉丝列表
     * 
     * @param userId 用户ID
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 好友关系列表
     */
    List<Friend> selectFollowerList(@Param("userId") Integer userId,
                                    @Param("offset") Integer offset,
                                    @Param("pageSize") Integer pageSize);

    /**
     * 查询用户的好友列表（双向关注）
     * 
     * @param userId 用户ID
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 好友关系列表
     */
    List<Friend> selectFriendList(@Param("userId") Integer userId,
                                  @Param("offset") Integer offset,
                                  @Param("pageSize") Integer pageSize);

    /**
     * 查询黑名单列表
     * 
     * @param userId 用户ID
     * @return 好友关系列表
     */
    List<Friend> selectBlacklist(@Param("userId") Integer userId);

    /**
     * 查询两个用户之间的关系
     * 
     * @param fromUserId 当前用户ID
     * @param toUserId 目标用户ID
     * @return 好友关系对象
     */
    Friend selectRelation(@Param("fromUserId") Integer fromUserId,
                          @Param("toUserId") Integer toUserId);

    /**
     * 新增关注关系
     * 
     * @param friend 好友关系对象
     * @return 影响行数
     */
    int insertFriend(Friend friend);

    /**
     * 更新好友关系状态
     * 
     * @param friend 好友关系对象
     * @return 影响行数
     */
    int updateFriendStatus(Friend friend);

    /**
     * 删除好友关系（取消关注）
     * 
     * @param fromUserId 当前用户ID
     * @param toUserId 目标用户ID
     * @return 影响行数
     */
    int deleteFriend(@Param("fromUserId") Integer fromUserId,
                     @Param("toUserId") Integer toUserId);

    /**
     * 查询关注数
     * 
     * @param userId 用户ID
     * @return 关注数
     */
    Integer selectFollowingCount(@Param("userId") Integer userId);

    /**
     * 查询粉丝数
     * 
     * @param userId 用户ID
     * @return 粉丝数
     */
    Integer selectFollowerCount(@Param("userId") Integer userId);

    /**
     * 查询好友数（双向关注）
     * 
     * @param userId 用户ID
     * @return 好友数
     */
    Integer selectFriendCount(@Param("userId") Integer userId);

    /**
     * 检查是否已关注
     * 
     * @param fromUserId 当前用户ID
     * @param toUserId 目标用户ID
     * @return 1-已关注，0-未关注
     */
    Integer checkIsFollowing(@Param("fromUserId") Integer fromUserId,
                             @Param("toUserId") Integer toUserId);
    
    // ==================== 兼容方法 ====================
    
    /**
     * 根据用户ID和关注者ID查询（兼容）
     */
    default Friend selectByUserIdAndFollowerId(Integer userId, Integer followerId) {
        return selectRelation(userId, followerId);
    }
    
    /**
     * 更新状态（兼容）
     */
    default int updateStatus(Friend friend) {
        return updateFriendStatus(friend);
    }
    
    /**
     * 插入关注（兼容）
     */
    default int insertFollow(Friend friend) {
        return insertFriend(friend);
    }
    
    /**
     * 删除关注（兼容）
     */
    default int deleteFollow(Integer userId, Integer followerId) {
        return deleteFriend(userId, followerId);
    }
    
    /**
     * 查询是否存在关注关系（兼容）
     */
    default Friend selectFollowExists(Integer userId, Integer followerId) {
        return selectRelation(userId, followerId);
    }
}
