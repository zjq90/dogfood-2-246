package com.it.service.impl;

import com.it.common.PageResult;
import com.it.common.Result;
import com.it.mapper.DoumailMapper;
import com.it.model.Doumail;
import com.it.service.DoumailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 豆邮服务实现类
 * 
 * @author weibo Team
 */
@Slf4j
@Service
public class DoumailServiceImpl implements DoumailService {

    @Resource
    private DoumailMapper doumailMapper;

    /**
     * 获取会话列表
     * 
     * @param userId   用户ID
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 会话列表
     */
    @Override
    public Result<PageResult<Doumail>> getConversationList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("get conversation list, userId: {}, pageNum: {}, pageSize: {}", userId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Doumail> list = doumailMapper.selectConversationList(userId, offset, pageSize);
        Long total = doumailMapper.selectConversationCount(userId);
        PageResult<Doumail> pageResult = PageResult.success(pageNum, pageSize, total, list);
        return Result.success("获取成功", pageResult);
    }

    /**
     * 获取豆邮详情
     * 
     * @param userId       当前用户ID
     * @param targetUserId 对方用户ID
     * @param pageNum      页码
     * @param pageSize     每页条数
     * @return 豆邮列表
     */
    @Override
    public Result<PageResult<Doumail>> getDoumailDetail(Integer userId, Integer targetUserId, Integer pageNum, Integer pageSize) {
        log.info("get doumail detail, userId: {}, targetUserId: {}, pageNum: {}, pageSize: {}", userId, targetUserId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Doumail> list = doumailMapper.selectDoumailDetail(userId, targetUserId, offset, pageSize);
        Long total = doumailMapper.selectDoumailDetailCount(userId, targetUserId);
        PageResult<Doumail> pageResult = PageResult.success(pageNum, pageSize, total, list);
        return Result.success("获取成功", pageResult);
    }

    /**
     * 发送豆邮
     * 
     * @param doumail 豆邮对象
     * @return 发送结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Doumail> sendDoumail(Doumail doumail) {
        log.info("send doumail, from: {}, to: {}", doumail.getFromUserId(), doumail.getToUserId());
        doumail.setCreateTime(new Date());
        doumail.setStatus(0);
        int result = doumailMapper.insertDoumail(doumail);
        return result > 0 ? Result.success("发送成功", doumail) : Result.error("发送失败");
    }

    /**
     * 标记为已读
     * 
     * @param fromUserId 发送者ID
     * @param toUserId   接收者ID
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> markAsRead(Integer fromUserId, Integer toUserId) {
        log.info("mark as read, fromUserId: {}, toUserId: {}", fromUserId, toUserId);
        int result = doumailMapper.updateStatus(fromUserId, toUserId, 1);
        return result > 0 ? Result.success("标记成功", true) : Result.error("标记失败");
    }

    /**
     * 删除豆邮
     * 
     * @param doumailId 豆邮ID
     * @param userId    操作人ID
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteDoumail(Integer doumailId, Integer userId) {
        log.info("delete doumail, doumailId: {}, userId: {}", doumailId, userId);
        int result = doumailMapper.deleteById(doumailId, userId);
        return result > 0 ? Result.success("删除成功", true) : Result.error("删除失败");
    }

    /**
     * 获取未读豆邮数
     * 
     * @param userId 用户ID
     * @return 未读数量
     */
    @Override
    public Result<Integer> getUnreadCount(Integer userId) {
        log.info("get unread count, userId: {}", userId);
        Integer count = doumailMapper.selectUnreadCount(userId);
        return Result.success("获取成功", count);
    }
}
