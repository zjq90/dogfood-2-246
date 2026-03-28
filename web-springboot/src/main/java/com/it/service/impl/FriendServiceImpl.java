package com.it.service.impl;

import com.it.common.PageResult;
import com.it.common.Result;
import com.it.mapper.FriendMapper;
import com.it.model.Friend;
import com.it.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 好友关系服务实现类
 * 
 * @author weibo Team
 */
@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

    @Resource
    private FriendMapper friendMapper;

    /**
     * 关注用户
     * 
     * @param fromUserId 关注者ID
     * @param toUserId   被关注者ID
     * @return 关注结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> follow(Integer fromUserId, Integer toUserId) {
        log.info("follow user, fromUserId: {}, toUserId: {}", fromUserId, toUserId);
        if (fromUserId.equals(toUserId)) {
            return Result.error("不能关注自己");
        }
        // 检查是否已经关注
        Friend existingFriend = friendMapper.selectByUserIdAndFollowerId(fromUserId, toUserId);
        if (existingFriend != null) {
            if (existingFriend.getStatus() == 1) {
                return Result.error("已经关注该用户");
            } else if (existingFriend.getStatus() == 2) {
                // 如果是拉黑状态，更新为关注状态
                existingFriend.setStatus(1);
                existingFriend.setCreateTime(new Date());
                int result = friendMapper.updateStatus(existingFriend);
                return result > 0 ? Result.success("关注成功", true) : Result.error("关注失败");
            }
        }
        Friend friend = new Friend();
        friend.setUserId(fromUserId);
        friend.setFollowerId(toUserId);
        friend.setStatus(1);
        friend.setCreateTime(new Date());
        int result = friendMapper.insertFollow(friend);
        return result > 0 ? Result.success("关注成功", true) : Result.error("关注失败");
    }

    /**
     * 取消关注
     * 
     * @param fromUserId 关注者ID
     * @param toUserId   被关注者ID
     * @return 取消关注结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> unfollow(Integer fromUserId, Integer toUserId) {
        log.info("unfollow user, fromUserId: {}, toUserId: {}", fromUserId, toUserId);
        int result = friendMapper.deleteFollow(fromUserId, toUserId);
        return result > 0 ? Result.success("取消关注成功", true) : Result.error("取消关注失败");
    }

    /**
     * 拉黑用户
     * 
     * @param fromUserId 操作者ID
     * @param toUserId   被拉黑者ID
     * @return 拉黑结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> block(Integer fromUserId, Integer toUserId) {
        log.info("block user, fromUserId: {}, toUserId: {}", fromUserId, toUserId);
        if (fromUserId.equals(toUserId)) {
            return Result.error("不能拉黑自己");
        }
        // 先删除关注关系（如果存在）
        friendMapper.deleteFollow(fromUserId, toUserId);
        // 检查是否已有拉黑记录
        Friend existingFriend = friendMapper.selectByUserIdAndFollowerId(fromUserId, toUserId);
        if (existingFriend != null) {
            if (existingFriend.getStatus() == 2) {
                return Result.error("已经拉黑该用户");
            }
            // 更新状态为拉黑
            existingFriend.setStatus(2);
            int result = friendMapper.updateStatus(existingFriend);
            return result > 0 ? Result.success("拉黑成功", true) : Result.error("拉黑失败");
        }
        Friend friend = new Friend();
        friend.setUserId(fromUserId);
        friend.setFollowerId(toUserId);
        friend.setStatus(2);
        friend.setCreateTime(new Date());
        int result = friendMapper.insertFollow(friend);
        return result > 0 ? Result.success("拉黑成功", true) : Result.error("拉黑失败");
    }

    /**
     * 取消拉黑
     * 
     * @param fromUserId 操作者ID
     * @param toUserId   被拉黑者ID
     * @return 取消拉黑结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> unblock(Integer fromUserId, Integer toUserId) {
        log.info("unblock user, fromUserId: {}, toUserId: {}", fromUserId, toUserId);
        int result = friendMapper.deleteFollow(fromUserId, toUserId);
        return result > 0 ? Result.success("取消拉黑成功", true) : Result.error("取消拉黑失败");
    }

    /**
     * 获取关注列表
     * 
     * @param userId   用户ID
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 关注列表
     */
    @Override
    public Result<PageResult<Friend>> getFollowingList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("get following list, userId: {}, pageNum: {}, pageSize: {}", userId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Friend> list = friendMapper.selectFollowingList(userId, offset, pageSize);
        Long total = Long.valueOf(friendMapper.selectFollowingCount(userId));
        PageResult<Friend> pageResult = PageResult.success(pageNum, pageSize, total, list);
        return Result.success("获取成功", pageResult);
    }

    /**
     * 获取粉丝列表
     * 
     * @param userId   用户ID
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 粉丝列表
     */
    @Override
    public Result<PageResult<Friend>> getFollowerList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("get follower list, userId: {}, pageNum: {}, pageSize: {}", userId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Friend> list = friendMapper.selectFollowerList(userId, offset, pageSize);
        Long total = Long.valueOf(friendMapper.selectFollowerCount(userId));
        PageResult<Friend> pageResult = PageResult.success(pageNum, pageSize, total, list);
        return Result.success("获取成功", pageResult);
    }

    /**
     * 获取好友列表（双向关注）
     * 
     * @param userId   用户ID
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 好友列表
     */
    @Override
    public Result<PageResult<Friend>> getFriendList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("get friend list, userId: {}, pageNum: {}, pageSize: {}", userId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Friend> list = friendMapper.selectFriendList(userId, offset, pageSize);
        Long total = Long.valueOf(friendMapper.selectFriendCount(userId));
        PageResult<Friend> pageResult = PageResult.success(pageNum, pageSize, total, list);
        return Result.success("获取成功", pageResult);
    }

    /**
     * 获取黑名单列表
     * 
     * @param userId 用户ID
     * @return 黑名单列表
     */
    @Override
    public Result<List<Friend>> getBlacklist(Integer userId) {
        log.info("get blacklist, userId: {}", userId);
        List<Friend> list = friendMapper.selectBlacklist(userId);
        return Result.success("获取成功", list);
    }

    /**
     * 检查是否已关注
     * 
     * @param fromUserId 当前用户ID
     * @param toUserId   目标用户ID
     * @return true-已关注，false-未关注
     */
    @Override
    public Result<Boolean> isFollowing(Integer fromUserId, Integer toUserId) {
        log.info("check is following, fromUserId: {}, toUserId: {}", fromUserId, toUserId);
        Friend friend = friendMapper.selectFollowExists(fromUserId, toUserId);
        boolean isFollowing = friend != null && friend.getStatus() == 1;
        return Result.success("查询成功", isFollowing);
    }

    /**
     * 获取关注数
     * 
     * @param userId 用户ID
     * @return 关注数
     */
    @Override
    public Result<Integer> getFollowingCount(Integer userId) {
        log.info("get following count, userId: {}", userId);
        Integer count = friendMapper.selectFollowingCount(userId);
        return Result.success("获取成功", count.intValue());
    }

    /**
     * 获取粉丝数
     * 
     * @param userId 用户ID
     * @return 粉丝数
     */
    @Override
    public Result<Integer> getFollowerCount(Integer userId) {
        log.info("get follower count, userId: {}", userId);
        Integer count = friendMapper.selectFollowerCount(userId);
        return Result.success("获取成功", count.intValue());
    }

    /**
     * 获取好友数
     * 
     * @param userId 用户ID
     * @return 好友数
     */
    @Override
    public Result<Integer> getFriendCount(Integer userId) {
        log.info("get friend count, userId: {}", userId);
        Integer count = friendMapper.selectFriendCount(userId);
        return Result.success("获取成功", count.intValue());
    }
}
