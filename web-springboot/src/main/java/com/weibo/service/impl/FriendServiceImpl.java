package com.weibo.service.impl;

import com.weibo.common.Result;
import com.weibo.dto.PageResult;
import com.weibo.mapper.FriendMapper;
import com.weibo.model.Friend;
import com.weibo.service.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    private static final Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);

    @Autowired
    private FriendMapper friendMapper;

    @Override
    @Transactional
    public Result<Boolean> follow(Integer fromUserId, Integer toUserId) {
        try {
            if (fromUserId.equals(toUserId)) {
                return Result.error("不能关注自己");
            }
            Friend friend = new Friend();
            friend.setFromUserId(fromUserId);
            friend.setToUserId(toUserId);
            friend.setState(1);
            friend.setTime(new Date());
            
            int result = friendMapper.insert(friend);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("关注失败");
        } catch (Exception e) {
            logger.error("关注用户异常", e);
            return Result.error("关注异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> unfollow(Integer fromUserId, Integer toUserId) {
        try {
            int result = friendMapper.delete(fromUserId, toUserId);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("取消关注失败");
        } catch (Exception e) {
            logger.error("取消关注异常", e);
            return Result.error("取消关注异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> block(Integer fromUserId, Integer toUserId) {
        try {
            Friend friend = new Friend();
            friend.setFromUserId(fromUserId);
            friend.setToUserId(toUserId);
            friend.setState(2);
            friend.setTime(new Date());
            
            int result = friendMapper.insert(friend);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("拉黑失败");
        } catch (Exception e) {
            logger.error("拉黑用户异常", e);
            return Result.error("拉黑异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> unblock(Integer fromUserId, Integer toUserId) {
        try {
            int result = friendMapper.delete(fromUserId, toUserId);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("取消拉黑失败");
        } catch (Exception e) {
            logger.error("取消拉黑异常", e);
            return Result.error("取消拉黑异常：" + e.getMessage());
        }
    }

    @Override
    public Result<PageResult<Friend>> getFollowingList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List<Friend> friends = friendMapper.selectFollowing(userId, offset, pageSize);
            int total = friendMapper.countFollowing(userId);
            
            PageResult<Friend> pageResult = new PageResult<>(friends, total, pageNum, pageSize);
            return Result.success(pageResult);
        } catch (Exception e) {
            logger.error("获取关注列表异常", e);
            return Result.error("获取关注列表异常：" + e.getMessage());
        }
    }

    @Override
    public Result<PageResult<Friend>> getFollowerList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List<Friend> friends = friendMapper.selectFollower(userId, offset, pageSize);
            int total = friendMapper.countFollower(userId);
            
            PageResult<Friend> pageResult = new PageResult<>(friends, total, pageNum, pageSize);
            return Result.success(pageResult);
        } catch (Exception e) {
            logger.error("获取粉丝列表异常", e);
            return Result.error("获取粉丝列表异常：" + e.getMessage());
        }
    }

    @Override
    public Result<PageResult<Friend>> getFriendList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List<Friend> friends = friendMapper.selectFriends(userId, offset, pageSize);
            int total = friendMapper.countFriends(userId);
            
            PageResult<Friend> pageResult = new PageResult<>(friends, total, pageNum, pageSize);
            return Result.success(pageResult);
        } catch (Exception e) {
            logger.error("获取好友列表异常", e);
            return Result.error("获取好友列表异常：" + e.getMessage());
        }
    }

    @Override
    public Result<List<Friend>> getBlacklist(Integer userId) {
        try {
            List<Friend> friends = friendMapper.selectBlacklist(userId);
            return Result.success(friends);
        } catch (Exception e) {
            logger.error("获取黑名单异常", e);
            return Result.error("获取黑名单异常：" + e.getMessage());
        }
    }

    @Override
    public Result<Boolean> isFollowing(Integer fromUserId, Integer toUserId) {
        try {
            boolean following = friendMapper.checkFollowing(fromUserId, toUserId) > 0;
            return Result.success(following);
        } catch (Exception e) {
            logger.error("检查关注状态异常", e);
            return Result.error("检查关注状态异常：" + e.getMessage());
        }
    }

    @Override
    public Result<Integer> getFollowingCount(Integer userId) {
        try {
            int count = friendMapper.countFollowing(userId);
            return Result.success(count);
        } catch (Exception e) {
            logger.error("获取关注数异常", e);
            return Result.error("获取关注数异常：" + e.getMessage());
        }
    }

    @Override
    public Result<Integer> getFollowerCount(Integer userId) {
        try {
            int count = friendMapper.countFollower(userId);
            return Result.success(count);
        } catch (Exception e) {
            logger.error("获取粉丝数异常", e);
            return Result.error("获取粉丝数异常：" + e.getMessage());
        }
    }

    @Override
    public Result<Integer> getFriendCount(Integer userId) {
        try {
            int count = friendMapper.countFriends(userId);
            return Result.success(count);
        } catch (Exception e) {
            logger.error("获取好友数异常", e);
            return Result.error("获取好友数异常：" + e.getMessage());
        }
    }
}
