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

/**
 * 豆邮服务实现类
 * 处理豆邮相关的业务逻辑
 * 
 * @author weibo Team
 */
@Service
public class DoumailServiceImpl implements DoumailService {

    private static final Logger logger = LoggerFactory.getLogger(DoumailServiceImpl.class);

    @Autowired
    private DoumailMapper doumailMapper;

    @Override
    public Result<PageResult<Doumail>> getConversationList(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List<Doumail> list = doumailMapper.selectConversationList(userId, offset, pageSize);
            Long total = doumailMapper.selectConversationCount(userId);
            
            PageResult<Doumail> pageResult = new PageResult<>(list, total.intValue(), pageNum, pageSize);
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
            List<Doumail> list = doumailMapper.selectDoumailDetail(userId, targetUserId, offset, pageSize);
            Long total = doumailMapper.selectDoumailCount(userId, targetUserId);
            
            PageResult<Doumail> pageResult = new PageResult<>(list, total.intValue(), pageNum, pageSize);
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
            doumail.setChatTime(new Date());
            doumail.setStatus(0);
            doumail.setRead(0);
            
            int result = doumailMapper.insertDoumail(doumail);
            if (result > 0) {
                return Result.success(doumail);
            }
            return Result.error("发送失败");
        } catch (Exception e) {
            logger.error("发送豆邮异常", e);
            return Result.error("发送豆邮异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> markAsRead(Integer fromUserId, Integer toUserId) {
        try {
            int result = doumailMapper.markAsRead(fromUserId, toUserId);
            return Result.success(true);
        } catch (Exception e) {
            logger.error("标记已读异常", e);
            return Result.error("标记已读异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> deleteDoumail(Integer doumailId, Integer userId) {
        try {
            int result = doumailMapper.deleteDoumail(doumailId, userId, 1);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("删除失败");
        } catch (Exception e) {
            logger.error("删除豆邮异常", e);
            return Result.error("删除豆邮异常：" + e.getMessage());
        }
    }

    @Override
    public Result<Integer> getUnreadCount(Integer userId) {
        try {
            Integer count = doumailMapper.selectUnreadCount(userId);
            return Result.success(count != null ? count : 0);
        } catch (Exception e) {
            logger.error("获取未读数异常", e);
            return Result.success(0);
        }
    }
}
