package com.weibo.service;

import com.weibo.common.Result;
import com.weibo.dto.PageResult;
import com.weibo.model.Doumail;

public interface DoumailService {

    Result<PageResult<Doumail>> getConversationList(Integer userId, Integer pageNum, Integer pageSize);

    Result<PageResult<Doumail>> getDoumailDetail(Integer userId, Integer targetUserId, Integer pageNum, Integer pageSize);

    Result<Doumail> sendDoumail(Doumail doumail);

    Result<Boolean> markAsRead(Integer fromUserId, Integer toUserId);

    Result<Boolean> deleteDoumail(Integer doumailId, Integer userId);

    Result<Integer> getUnreadCount(Integer userId);
}
