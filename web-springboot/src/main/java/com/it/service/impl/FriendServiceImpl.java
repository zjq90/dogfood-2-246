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

@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

    @Resource
    private FriendMapper friendMapper;

    @Override
    public Result<Boolean> follow(Integer fromUserId, Integer toUserId) {
        log.info("follow, fromUserId: {}, toUserId: {}", fromUserId, toUserId);
        if (fromUserId.equals(toUserId)) {
            return Result.error("Cannot follow yourself");
        }
        Friend friend = new Friend();
        friend.setFromUserId(fromUserId);
        friend.setToUserId(toUserId);
        friend.setUserId(fromUserId);
        friend.setFollowerId(toUserId);
        friend.setCreateTime(new Date());
        friend.setStatus(1);
        
        int count = friendMapper.selectFollowExists(fromUserId, toUserId);
        if (count > 0) {
            return Result.error("Already followed");
        }
        
        int result = friendMapper.insertFollow(friend);
        return result > 0 ? Result.success("Follow success", true) : Result.error("Follow failed");
    }

    @Override
    public Result<Boolean> unfollow(Integer fromUserId, Integer toUserId) {
        log.info("unfollow, fromUserId: {}, toUserId: {}", fromUserId, toUserId);
        int result = friendMapper.deleteFollow(fromUserId, toUserId);
        return result > 0 ? Result.success("Unfollow success", true) : Result.error("Unfollow failed");
    }

    @Override
    public Result<Boolean> block(Integer fromUserId, Integer toUserId) {
        log.info("block, fromUserId: {}, toUserId: {}", fromUserId, toUserId);
        int result = friendMapper.updateStatus(fromUserId, toUserId, 2);
        return result > 0 ? Result.success("Block success", true) : Result.error("Block failed");
    }

    @Override
    public Result<Boolean> unblock(Integer fromUserId, Integer toUserId) {
        log.info("unblock, fromUserId: {}, toUserId: {}", fromUserId, toUserId);
        int result = friendMapper.updateStatus(fromUserId, toUserId, 1);
        return result > 0 ? Result.success("Unblock success", true) : Result.error("Unblock failed");
    }

    @Override
    public Result<PageResult<Friend>> getFollowingList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("get following list, userId: {}, pageNum: {}, pageSize: {}", userId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Friend> following = friendMapper.selectFollowingList(userId, offset, pageSize);
        Integer count = friendMapper.selectFollowingCount(userId);
        Long total = count.longValue();
        PageResult<Friend> pageResult = PageResult.of(pageNum, pageSize, total, following);
        return Result.success(pageResult);
    }

    @Override
    public Result<PageResult<Friend>> getFollowerList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("get follower list, userId: {}, pageNum: {}, pageSize: {}", userId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Friend> followers = friendMapper.selectFollowerList(userId, offset, pageSize);
        Integer count = friendMapper.selectFollowerCount(userId);
        Long total = count.longValue();
        PageResult<Friend> pageResult = PageResult.of(pageNum, pageSize, total, followers);
        return Result.success(pageResult);
    }

    @Override
    public Result<PageResult<Friend>> getFriendList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("get friend list, userId: {}, pageNum: {}, pageSize: {}", userId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Friend> friends = friendMapper.selectFriendList(userId, offset, pageSize);
        Integer count = friendMapper.selectFriendCount(userId);
        Long total = count.longValue();
        PageResult<Friend> pageResult = PageResult.of(pageNum, pageSize, total, friends);
        return Result.success(pageResult);
    }

    @Override
    public Result<List<Friend>> getBlacklist(Integer userId) {
        log.info("get blacklist, userId: {}", userId);
        List<Friend> blacklist = friendMapper.selectBlacklist(userId);
        return Result.success(blacklist);
    }

    @Override
    public Result<Boolean> isFollowing(Integer fromUserId, Integer toUserId) {
        int count = friendMapper.selectFollowExists(fromUserId, toUserId);
        return Result.success(count > 0);
    }

    @Override
    public Result<Integer> getFollowingCount(Integer userId) {
        log.info("get following count, userId: {}", userId);
        Integer count = friendMapper.selectFollowingCount(userId);
        return Result.success(count);
    }

    @Override
    public Result<Integer> getFollowerCount(Integer userId) {
        log.info("get follower count, userId: {}", userId);
        Integer count = friendMapper.selectFollowerCount(userId);
        return Result.success(count);
    }

    @Override
    public Result<Integer> getFriendCount(Integer userId) {
        log.info("get friend count, userId: {}", userId);
        Integer count = friendMapper.selectFriendCount(userId);
        return Result.success(count);
    }
}
