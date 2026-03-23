package com.it.service;

import com.it.common.PageResult;
import com.it.common.Result;
import com.it.model.Doumail;


public interface DoumailService {

    
    Result<PageResult<Doumail>> getConversationList(Integer userId, Integer pageNum, Integer pageSize);

    
    Result<PageResult<Doumail>> getDoumailDetail(Integer userId, Integer targetUserId, Integer pageNum, Integer pageSize);

    
    Result<Doumail> sendDoumail(Doumail doumail);

    
    Result<Boolean> markAsRead(Integer fromUserId, Integer toUserId);

    
    Result<Boolean> deleteDoumail(Integer doumailId, Integer userId);

    
    Result<Integer> getUnreadCount(Integer userId);
}
