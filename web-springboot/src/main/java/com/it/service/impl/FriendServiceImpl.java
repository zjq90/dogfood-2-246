package com.weibo.service.impl;

import com.weibo.dto.PageResult;
import com.weibo.dto.Result;
import com.weibo.mapper.FriendMapper;
import com.weibo.model.Friend;
import com.weibo.service.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 好友关系服务实现类
 * 实现好友关系相关的业务逻辑
 *
 * @author weibo Team
 */
@Service
public class FriendServiceImpl implements FriendService {

    private static final Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);

    @Autowired
    private FriendMapper friendMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> follow(Integer fromUserId, Integer toUserId) {
        if (fromUserId == null || toUserId == null) {
            return Result.error("用户ID不能为空");
        }
        if (fromUserId.equals(toUserId)) {
            return Result.error("不能关注自己");
        }

        // 检查是否已存在关系
        Friend existing = friendMapper.selectRelation(fromUserId, toUserId);
        if (existing != null) {
            if (existing.getStatus() == 1 || existing.getStatus() == 2) {
                return Result.error("已关注该用户");
            }
            // 更新状态为关注
            existing.setStatus(1);
            friendMapper.updateFriendStatus(existing);
        } else {
            // 新增关注关系
            Friend friend = new Friend();
            friend.setFromUserId(fromUserId);
            friend.setToUserId(toUserId);
            friend.setStatus(1);
            friendMapper.insertFriend(friend);
        }

        // 检查对方是否已关注我，如果是则更新为双向好友
        Friend reverse = friendMapper.selectRelation(toUserId, fromUserId);
        if (reverse != null && (reverse.getStatus() == 1 || reverse.getStatus() == 2)) {
            existing = friendMapper.selectRelation(fromUserId, toUserId);
            if (existing != null) {
                existing.setStatus(2);
                friendMapper.updateFriendStatus(existing);
            }
            reverse.setStatus(2);
            friendMapper.updateFriendStatus(reverse);
        }

        logger.info("关注成功: {} -> {}", fromUserId, toUserId);
        return Result.success("关注成功", true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> unfollow(Integer fromUserId, Integer toUserId) {
        if (fromUserId == null || toUserId == null) {
            return Result.error("用户ID不能为空");
        }

        // 删除关注关系
        friendMapper.deleteFriend(fromUserId, toUserId);

        // 如果对方关注了我，更新对方状态为单向关注
        Friend reverse = friendMapper.selectRelation(toUserId, fromUserId);
        if (reverse != null && reverse.getStatus() == 2) {
            reverse.setStatus(1);
            friendMapper.updateFriendStatus(reverse);
        }

        logger.info("取消关注成功: {} -> {}", fromUserId, toUserId);
        return Result.success("取消关注成功", true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> block(Integer fromUserId, Integer toUserId) {
        if (fromUserId == null || toUserId == null) {
            return Result.error("用户ID不能为空");
        }
        if (fromUserId.equals(toUserId)) {
            return Result.error("不能拉黑自己");
        }

        Friend existing = friendMapper.selectRelation(fromUserId, toUserId);
        if (existing != null) {
            existing.setStatus(3);
            friendMapper.updateFriendStatus(existing);
        } else {
            Friend friend = new Friend();
            friend.setFromUserId(fromUserId);
            friend.setToUserId(toUserId);
            friend.setStatus(3);
            friendMapper.insertFriend(friend);
        }

        logger.info("拉黑成功: {} -> {}", fromUserId, toUserId);
        return Result.success("拉黑成功", true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> unblock(Integer fromUserId, Integer toUserId) {
        if (fromUserId == null || toUserId == null) {
            return Result.error("用户ID不能为空");
        }

        Friend existing = friendMapper.selectRelation(fromUserId, toUserId);
        if (existing != null && existing.getStatus() == 3) {
            friendMapper.deleteFriend(fromUserId, toUserId);
        }

        logger.info("取消拉黑成功: {} -> {}", fromUserId, toUserId);
        return Result.success("取消拉黑成功", true);
    }

    @Override
    public Result<PageResult<Friend>> getFollowingList(Integer userId, Integer pageNum, Integer pageSize) {
        if (userId == null) {
            return Result.error("用户ID不能为空");
        }
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        int offset = (pageNum - 1) * pageSize;
        List<Friend> list = friendMapper.selectFollowingList(userId, offset, pageSize);
        Integer count = friendMapper.selectFollowingCount(userId);

        PageResult<Friend> pageResult = new PageResult<>(pageNum, pageSize, count != null ? count.longValue() : 0L, list);
        return Result.success("获取成功", pageResult);
    }

    @Override
    public Result<PageResult<Friend>> getFollowerList(Integer userId, Integer pageNum, Integer pageSize) {
        if (userId == null) {
            return Result.error("用户ID不能为空");
        }
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        int offset = (pageNum - 1) * pageSize;
        List<Friend> list = friendMapper.selectFollowerList(userId, offset, pageSize);
        Integer count = friendMapper.selectFollowerCount(userId);

        PageResult<Friend> pageResult = new PageResult<>(pageNum, pageSize, count != null ? count.longValue() : 0L, list);
        return Result.success("获取成功", pageResult);
    }

    @Override
    public Result<PageResult<Friend>> getFriendList(Integer userId, Integer pageNum, Integer pageSize) {
        if (userId == null) {
            return Result.error("用户ID不能为空");
        }
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        int offset = (pageNum - 1) * pageSize;
        List<Friend> list = friendMapper.selectFriendList(userId, offset, pageSize);
        Integer count = friendMapper.selectFriendCount(userId);

        PageResult<Friend> pageResult = new PageResult<>(pageNum, pageSize, count != null ? count.longValue() : 0L, list);
        return Result.success("获取成功", pageResult);
    }

    @Override
    public Result<List<Friend>> getBlacklist(Integer userId) {
        if (userId == null) {
            return Result.error("用户ID不能为空");
        }

        List<Friend> list = friendMapper.selectBlacklist(userId);
        return Result.success("获取成功", list);
    }

    @Override
    public Result<Boolean> isFollowing(Integer fromUserId, Integer toUserId) {
        if (fromUserId == null || toUserId == null) {
            return Result.success(false);
        }

        Integer count = friendMapper.checkIsFollowing(fromUserId, toUserId);
        return Result.success("查询成功", count != null && count > 0);
    }

    @Override
    public Result<Integer> getFollowingCount(Integer userId) {
        if (userId == null) {
            return Result.success(0);
        }

        Integer count = friendMapper.selectFollowingCount(userId);
        return Result.success("获取成功", count != null ? count : 0);
    }

    @Override
    public Result<Integer> getFollowerCount(Integer userId) {
        if (userId == null) {
            return Result.success(0);
        }

        Integer count = friendMapper.selectFollowerCount(userId);
        return Result.success("获取成功", count != null ? count : 0);
    }

    @Override
    public Result<Integer> getFriendCount(Integer userId) {
        if (userId == null) {
            return Result.success(0);
        }

        Integer count = friendMapper.selectFriendCount(userId);
        return Result.success("获取成功", count != null ? count : 0);
    }
}
