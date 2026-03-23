package com.weibo.service.impl;

import com.weibo.common.Result;
import com.weibo.dto.PageResult;
import com.weibo.mapper.DoumailMapper;
import com.weibo.model.Doumail;
import com.weibo.service.DoumailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DoumailServiceImpl implements DoumailService {

    private static final Logger logger = LoggerFactory.getLogger(DoumailServiceImpl.class);

    @Autowired
    private DoumailMapper doumailMapper;

    @Override
    public Result<PageResult<Doumail>> getConversationList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List<Doumail> doumails = doumailMapper.selectConversationList(userId, offset, pageSize);
            int total = doumailMapper.countConversationList(userId);
            
            PageResult<Doumail> pageResult = new PageResult<>(doumails, total, pageNum, pageSize);
            return Result.success(pageResult);
        } catch (Exception e) {
            logger.error("获取会话列表异常", e);
            return Result.error("获取会话列表异常：" + e.getMessage());
        }
    }

    @Override
    public Result<PageResult<Doumail>> getDoumailDetail(Integer userId, Integer targetUserId, Integer pageNum, Integer pageSize) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List<Doumail> doumails = doumailMapper.selectByUserIds(userId, targetUserId, offset, pageSize);
            int total = doumailMapper.countByUserIds(userId, targetUserId);
            
            PageResult<Doumail> pageResult = new PageResult<>(doumails, total, pageNum, pageSize);
            return Result.success(pageResult);
        } catch (Exception e) {
            logger.error("获取豆邮详情异常", e);
            return Result.error("获取豆邮详情异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Doumail> sendDoumail(Doumail doumail) {
        try {
            doumail.setTime(new Date());
            doumail.setIsRead(0);
            
            int result = doumailMapper.insert(doumail);
            if (result > 0) {
                return Result.success(doumail);
            }
            return Result.error("发送豆邮失败");
        } catch (Exception e) {
            logger.error("发送豆邮异常", e);
            return Result.error("发送豆邮异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> markAsRead(Integer fromUserId, Integer toUserId) {
        try {
            int result = doumailMapper.updateReadStatus(fromUserId, toUserId);
            if (result >= 0) {
                return Result.success(true);
            }
            return Result.error("标记已读失败");
        } catch (Exception e) {
            logger.error("标记已读异常", e);
            return Result.error("标记已读异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> deleteDoumail(Integer doumailId, Integer userId) {
        try {
            int result = doumailMapper.delete(doumailId, userId);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("删除豆邮失败");
        } catch (Exception e) {
            logger.error("删除豆邮异常", e);
            return Result.error("删除豆邮异常：" + e.getMessage());
        }
    }

    @Override
    public Result<Integer> getUnreadCount(Integer userId) {
        try {
            int count = doumailMapper.countUnread(userId);
            return Result.success(count);
        } catch (Exception e) {
            logger.error("获取未读豆邮数异常", e);
            return Result.error("获取未读豆邮数异常：" + e.getMessage());
        }
    }
}
