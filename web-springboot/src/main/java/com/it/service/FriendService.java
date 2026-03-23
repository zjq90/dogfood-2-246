package com.weibo.service;

import com.weibo.dto.PageResult;
import com.weibo.dto.Result;
import com.weibo.model.Friend;
import java.util.List;

/**
 * 好友关系服务接口
 * 
 * @author weibo Team
 */
public interface FriendService {

    /**
     * 关注用户
     * 
     * @param fromUserId 关注者ID
     * @param toUserId 被关注者ID
     * @return 关注结果
     */
    Result<Boolean> follow(Integer fromUserId, Integer toUserId);

    /**
     * 取消关注
     * 
     * @param fromUserId 关注者ID
     * @param toUserId 被关注者ID
     * @return 取消关注结果
     */
    Result<Boolean> unfollow(Integer fromUserId, Integer toUserId);

    /**
     * 拉黑用户
     * 
     * @param fromUserId 操作者ID
     * @param toUserId 被拉黑者ID
     * @return 拉黑结果
     */
    Result<Boolean> block(Integer fromUserId, Integer toUserId);

    /**
     * 取消拉黑
     * 
     * @param fromUserId 操作者ID
     * @param toUserId 被拉黑者ID
     * @return 取消拉黑结果
     */
    Result<Boolean> unblock(Integer fromUserId, Integer toUserId);

    /**
     * 获取关注列表
     * 
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 关注列表
     */
    Result<PageResult<Friend>> getFollowingList(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 获取粉丝列表
     * 
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 粉丝列表
     */
    Result<PageResult<Friend>> getFollowerList(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 获取好友列表（双向关注）
     * 
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 好友列表
     */
    Result<PageResult<Friend>> getFriendList(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 获取黑名单列表
     * 
     * @param userId 用户ID
     * @return 黑名单列表
     */
    Result<List<Friend>> getBlacklist(Integer userId);

    /**
     * 检查是否已关注
     * 
     * @param fromUserId 当前用户ID
     * @param toUserId 目标用户ID
     * @return true-已关注，false-未关注
     */
    Result<Boolean> isFollowing(Integer fromUserId, Integer toUserId);

    /**
     * 获取关注数
     * 
     * @param userId 用户ID
     * @return 关注数
     */
    Result<Integer> getFollowingCount(Integer userId);

    /**
     * 获取粉丝数
     * 
     * @param userId 用户ID
     * @return 粉丝数
     */
    Result<Integer> getFollowerCount(Integer userId);

    /**
     * 获取好友数
     * 
     * @param userId 用户ID
     * @return 好友数
     */
    Result<Integer> getFriendCount(Integer userId);
}
