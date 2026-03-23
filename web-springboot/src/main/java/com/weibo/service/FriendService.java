package com.weibo.service;

import com.weibo.common.Result;
import com.weibo.dto.PageResult;
import com.weibo.model.Friend;
import java.util.List;

public interface FriendService {

    Result<Boolean> follow(Integer fromUserId, Integer toUserId);

    Result<Boolean> unfollow(Integer fromUserId, Integer toUserId);

    Result<Boolean> block(Integer fromUserId, Integer toUserId);

    Result<Boolean> unblock(Integer fromUserId, Integer toUserId);

    Result<PageResult<Friend>> getFollowingList(Integer userId, Integer pageNum, Integer pageSize);

    Result<PageResult<Friend>> getFollowerList(Integer userId, Integer pageNum, Integer pageSize);

    Result<PageResult<Friend>> getFriendList(Integer userId, Integer pageNum, Integer pageSize);

    Result<List<Friend>> getBlacklist(Integer userId);

    Result<Boolean> isFollowing(Integer fromUserId, Integer toUserId);

    Result<Integer> getFollowingCount(Integer userId);

    Result<Integer> getFollowerCount(Integer userId);

    Result<Integer> getFriendCount(Integer userId);
}
