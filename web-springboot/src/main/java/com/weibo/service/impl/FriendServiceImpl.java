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

import java.util.List;

/**
 * 好友关系服务实现类
 * 处理好友关系相关的业务逻辑
 * 
 * @author weibo Team
 */
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
            
            Friend existing = friendMapper.selectRelation(fromUserId, toUserId);
            if (existing != null) {
                if (existing.getStatus() == 1) {
                    return Result.error("已关注该用户");
                } else if (existing.getStatus() == 3) {
                    return Result.error("该用户在黑名单中");
                }
            }
            
            Friend friend = new Friend();
            friend.setFromUserId(fromUserId);
            friend.setToUserId(toUserId);
            friend.setStatus(1);
            
            int result = friendMapper.insertFriend(friend);
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
            int result = friendMapper.deleteFriend(fromUserId, toUserId);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("未关注该用户");
        } catch (Exception e) {
            logger.error("取消关注异常", e);
            return Result.error("取消关注异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> block(Integer fromUserId, Integer toUserId) {
        try {
            Friend friend = friendMapper.selectRelation(fromUserId, toUserId);
            if (friend == null) {
                friend = new Friend();
                friend.setFromUserId(fromUserId);
                friend.setToUserId(toUserId);
                friend.setStatus(3);
                friendMapper.insertFriend(friend);
            } else {
                friend.setStatus(3);
                friendMapper.updateFriendStatus(friend);
            }
            return Result.success(true);
        } catch (Exception e) {
            logger.error("拉黑用户异常", e);
            return Result.error("拉黑异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> unblock(Integer fromUserId, Integer toUserId) {
        try {
            int result = friendMapper.deleteFriend(fromUserId, toUserId);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("未拉黑该用户");
        } catch (Exception e) {
            logger.error("取消拉黑异常", e);
            return Result.error("取消拉黑异常：" + e.getMessage());
        }
    }

    @Override
    public Result<PageResult<Friend>> getFollowingList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List<Friend> list = friendMapper.selectFollowingList(userId, offset, pageSize);
            Integer total = friendMapper.selectFollowingCount(userId);
            
            PageResult<Friend> pageResult = new PageResult<>(list, total, pageNum, pageSize);
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
            List<Friend> list = friendMapper.selectFollowerList(userId, offset, pageSize);
            Integer total = friendMapper.selectFollowerCount(userId);
            
            PageResult<Friend> pageResult = new PageResult<>(list, total, pageNum, pageSize);
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
            List<Friend> list = friendMapper.selectFriendList(userId, offset, pageSize);
            Integer total = friendMapper.selectFriendCount(userId);
            
            PageResult<Friend> pageResult = new PageResult<>(list, total, pageNum, pageSize);
            return Result.success(pageResult);
        } catch (Exception e) {
            logger.error("获取好友列表异常", e);
            return Result.error("获取好友列表异常：" + e.getMessage());
        }
    }

    @Override
    public Result<List<Friend>> getBlacklist(Integer userId) {
        try {
            List<Friend> list = friendMapper.selectBlacklist(userId);
            return Result.success(list);
        } catch (Exception e) {
            logger.error("获取黑名单异常", e);
            return Result.error("获取黑名单异常：" + e.getMessage());
        }
    }

    @Override
    public Result<Boolean> isFollowing(Integer fromUserId, Integer toUserId) {
        try {
            Integer result = friendMapper.checkIsFollowing(fromUserId, toUserId);
            return Result.success(result != null && result > 0);
        } catch (Exception e) {
            logger.error("检查关注状态异常", e);
            return Result.success(false);
        }
    }

    @Override
    public Result<Integer> getFollowingCount(Integer userId) {
        try {
            Integer count = friendMapper.selectFollowingCount(userId);
            return Result.success(count != null ? count : 0);
        } catch (Exception e) {
            logger.error("获取关注数异常", e);
            return Result.success(0);
        }
    }

    @Override
    public Result<Integer> getFollowerCount(Integer userId) {
        try {
            Integer count = friendMapper.selectFollowerCount(userId);
            return Result.success(count != null ? count : 0);
        } catch (Exception e) {
            logger.error("获取粉丝数异常", e);
            return Result.success(0);
        }
    }

    @Override
    public Result<Integer> getFriendCount(Integer userId) {
        try {
            Integer count = friendMapper.selectFriendCount(userId);
            return Result.success(count != null ? count : 0);
        } catch (Exception e) {
            logger.error("获取好友数异常", e);
            return Result.success(0);
        }
    }
}
