package com.weibo.service.impl;

import com.weibo.dto.PageResult;
import com.weibo.dto.Result;
import com.weibo.mapper.DoumailMapper;
import com.weibo.model.Doumail;
import com.weibo.service.DoumailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 豆邮服务实现类
 */
@Slf4j
@Service
public class DoumailServiceImpl implements DoumailService {

    @Autowired
    private DoumailMapper doumailMapper;

    @Override
    public Result<PageResult<Doumail>> getConversationList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("获取会话列表: userId={}", userId);
        
        int offset = (pageNum - 1) * pageSize;
        List<Doumail> doumails = doumailMapper.selectConversationList(userId, offset, pageSize);
        Long total = doumailMapper.selectConversationCount(userId);
        
        PageResult<Doumail> pageResult = PageResult.build(doumails, total, pageNum, pageSize);
        return Result.success(pageResult);
    }

    @Override
    public Result<PageResult<Doumail>> getDoumailDetail(Integer userId, Integer targetUserId, Integer pageNum, Integer pageSize) {
        log.info("获取豆邮详情: userId={}, targetUserId={}", userId, targetUserId);
        
        int offset = (pageNum - 1) * pageSize;
        List<Doumail> doumails = doumailMapper.selectDoumailDetail(userId, targetUserId, offset, pageSize);
        Long total = doumailMapper.selectDoumailCount(userId, targetUserId);
        
        // 标记为已读
        doumailMapper.markAsRead(targetUserId, userId);
        
        PageResult<Doumail> pageResult = PageResult.build(doumails, total, pageNum, pageSize);
        return Result.success(pageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Doumail> sendDoumail(Doumail doumail) {
        log.info("发送豆邮: fromUserId={}, toUserId={}", doumail.getFromUserId(), doumail.getToUserId());
        
        if (doumail.getFromUserId().equals(doumail.getToUserId())) {
            return Result.error("不能给自己发送豆邮");
        }
        
        if (doumail.getContent() == null || doumail.getContent().isEmpty()) {
            return Result.error("内容不能为空");
        }

        doumail.setSendTime(new Date());
        doumail.setIsRead(0);

        int result = doumailMapper.insertDoumail(doumail);
        if (result > 0) {
            return Result.success("发送成功", doumail);
        } else {
            return Result.error("发送失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> markAsRead(Integer fromUserId, Integer toUserId) {
        log.info("标记为已读: fromUserId={}, toUserId={}", fromUserId, toUserId);
        
        int result = doumailMapper.markAsRead(fromUserId, toUserId);
        return Result.success("操作成功", result > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteDoumail(Integer doumailId, Integer userId) {
        log.info("删除豆邮: doumailId={}", doumailId);
        
        Doumail doumail = doumailMapper.selectById(doumailId);
        if (doumail == null) {
            return Result.error("豆邮不存在");
        }
        
        // 只能删除自己发送或接收的豆邮
        if (!doumail.getFromUserId().equals(userId) && !doumail.getToUserId().equals(userId)) {
            return Result.error("无权删除该豆邮");
        }

        int result = doumailMapper.delete(doumailId);
        if (result > 0) {
            return Result.success("删除成功", true);
        } else {
            return Result.error("删除失败");
        }
    }

    @Override
    public Result<Integer> getUnreadCount(Integer userId) {
        log.info("获取未读豆邮数: userId={}", userId);
        
        Integer count = doumailMapper.selectUnreadCount(userId);
        return Result.success(count);
    }
}
