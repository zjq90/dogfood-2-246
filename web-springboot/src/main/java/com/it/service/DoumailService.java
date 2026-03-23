package com.weibo.service;

import com.weibo.dto.PageResult;
import com.weibo.dto.Result;
import com.weibo.entity.Doumail;

/**
 * 豆邮服务接口
 * 
 * @author weibo Team
 */
public interface DoumailService {

    /**
     * 获取会话列表
     * 
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 会话列表
     */
    Result<PageResult<Doumail>> getConversationList(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 获取豆邮详情
     * 
     * @param userId 当前用户ID
     * @param targetUserId 对方用户ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 豆邮列表
     */
    Result<PageResult<Doumail>> getDoumailDetail(Integer userId, Integer targetUserId, Integer pageNum, Integer pageSize);

    /**
     * 发送豆邮
     * 
     * @param doumail 豆邮对象
     * @return 发送结果
     */
    Result<Doumail> sendDoumail(Doumail doumail);

    /**
     * 标记为已读
     * 
     * @param fromUserId 发送者ID
     * @param toUserId 接收者ID
     * @return 操作结果
     */
    Result<Boolean> markAsRead(Integer fromUserId, Integer toUserId);

    /**
     * 删除豆邮
     * 
     * @param doumailId 豆邮ID
     * @param userId 操作人ID
     * @return 删除结果
     */
    Result<Boolean> deleteDoumail(Integer doumailId, Integer userId);

    /**
     * 获取未读豆邮数
     * 
     * @param userId 用户ID
     * @return 未读数量
     */
    Result<Integer> getUnreadCount(Integer userId);
}
