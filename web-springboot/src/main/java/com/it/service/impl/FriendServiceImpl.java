package com.weibo.service.impl;

import com.weibo.dto.PageResult;
import com.weibo.dto.Result;
import com.weibo.mapper.FriendMapper;
import com.weibo.model.Friend;
import com.weibo.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 好友关系服务实现类
 */
@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> follow(Integer fromUserId, Integer toUserId) {
        log.info("关注用户: fromUserId={}, toUserId={}", fromUserId, toUserId);
        
        if (fromUserId.equals(toUserId)) {
            return Result.error("不能关注自己");
        }

        // 检查是否已关注
        Friend existing = friendMapper.selectByUserIds(fromUserId, toUserId);
        if (existing != null) {
            if (existing.getStatus() == 1 || existing.getStatus() == 2) {
                return Result.error("已关注该用户");
            } else if (existing.getStatus() == 3) {
                // 从拉黑状态改为关注
                friendMapper.updateStatus(existing.getFriendId(), 1);
                return Result.success("关注成功", true);
            }
        }

        Friend friend = new Friend();
        friend.setFromUserId(fromUserId);
        friend.setToUserId(toUserId);
        friend.setStatus(1);

        int result = friendMapper.insert(friend);
        if (result > 0) {
            // 检查对方是否已关注自己，如果是则更新为双向关注
            Friend reverse = friendMapper.selectByUserIds(toUserId, fromUserId);
            if (reverse != null && (reverse.getStatus() == 1 || reverse.getStatus() == 2)) {
                friendMapper.updateStatus(friend.getFriendId(), 2);
                friendMapper.updateStatus(reverse.getFriendId(), 2);
            }
            return Result.success("关注成功", true);
        } else {
            return Result.error("关注失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> unfollow(Integer fromUserId, Integer toUserId) {
        log.info("取消关注: fromUserId={}, toUserId={}", fromUserId, toUserId);
        
        Friend friend = friendMapper.selectByUserIds(fromUserId, toUserId);
        if (friend == null || (friend.getStatus() != 1 && friend.getStatus() != 2)) {
            return Result.error("未关注该用户");
        }

        int result = friendMapper.delete(friend.getFriendId());
        if (result > 0) {
            // 如果对方关注了自己，更新对方状态为单向关注
            Friend reverse = friendMapper.selectByUserIds(toUserId, fromUserId);
            if (reverse != null && reverse.getStatus() == 2) {
                friendMapper.updateStatus(reverse.getFriendId(), 1);
            }
            return Result.success("取消关注成功", true);
        } else {
            return Result.error("取消关注失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> block(Integer fromUserId, Integer toUserId) {
        log.info("拉黑用户: fromUserId={}, toUserId={}", fromUserId, toUserId);
        
        if (fromUserId.equals(toUserId)) {
            return Result.error("不能拉黑自己");
        }

        Friend existing = friendMapper.selectByUserIds(fromUserId, toUserId);
        if (existing != null) {
            friendMapper.updateStatus(existing.getFriendId(), 3);
            return Result.success("拉黑成功", true);
        } else {
            Friend friend = new Friend();
            friend.setFromUserId(fromUserId);
            friend.setToUserId(toUserId);
            friend.setStatus(3);
            
            int result = friendMapper.insert(friend);
            if (result > 0) {
                return Result.success("拉黑成功", true);
            } else {
                return Result.error("拉黑失败");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> unblock(Integer fromUserId, Integer toUserId) {
        log.info("取消拉黑: fromUserId={}, toUserId={}", fromUserId, toUserId);
        
        Friend friend = friendMapper.selectByUserIds(fromUserId, toUserId);
        if (friend == null || friend.getStatus() != 3) {
            return Result.error("未拉黑该用户");
        }

        int result = friendMapper.delete(friend.getFriendId());
        if (result > 0) {
            return Result.success("取消拉黑成功", true);
        } else {
            return Result.error("取消拉黑失败");
        }
    }

    @Override
    public Result<PageResult<Friend>> getFollowingList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("获取关注列表: userId={}", userId);
        
        int offset = (pageNum - 1) * pageSize;
        List<Friend> friends = friendMapper.selectFollowingList(userId, offset, pageSize);
        Long total = friendMapper.selectFollowingCount(userId);
        
        PageResult<Friend> pageResult = PageResult.build(friends, total, pageNum, pageSize);
        return Result.success(pageResult);
    }

    @Override
    public Result<PageResult<Friend>> getFollowerList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("获取粉丝列表: userId={}", userId);
        
        int offset = (pageNum - 1) * pageSize;
        List<Friend> friends = friendMapper.selectFollowerList(userId, offset, pageSize);
        Long total = friendMapper.selectFollowerCount(userId);
        
        PageResult<Friend> pageResult = PageResult.build(friends, total, pageNum, pageSize);
        return Result.success(pageResult);
    }

    @Override
    public Result<PageResult<Friend>> getFriendList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("获取好友列表: userId={}", userId);
        
        int offset = (pageNum - 1) * pageSize;
        List<Friend> friends = friendMapper.selectFriendList(userId, offset, pageSize);
        Long total = friendMapper.selectFriendCount(userId);
        
        PageResult<Friend> pageResult = PageResult.build(friends, total, pageNum, pageSize);
        return Result.success(pageResult);
    }

    @Override
    public Result<List<Friend>> getBlacklist(Integer userId) {
        log.info("获取黑名单列表: userId={}", userId);
        
        List<Friend> friends = friendMapper.selectBlacklist(userId);
        return Result.success(friends);
    }

    @Override
    public Result<Boolean> isFollowing(Integer fromUserId, Integer toUserId) {
        Friend friend = friendMapper.selectByUserIds(fromUserId, toUserId);
        boolean isFollowing = friend != null && (friend.getStatus() == 1 || friend.getStatus() == 2);
        return Result.success(isFollowing);
    }

    @Override
    public Result<Long> getFollowingCount(Integer userId) {
        Long count = friendMapper.selectFollowingCount(userId);
        return Result.success(count);
    }

    @Override
    public Result<Long> getFollowerCount(Integer userId) {
        Long count = friendMapper.selectFollowerCount(userId);
        return Result.success(count);
    }

    @Override
    public Result<Long> getFriendCount(Integer userId) {
        Long count = friendMapper.selectFriendCount(userId);
        return Result.success(count);
    }
}
