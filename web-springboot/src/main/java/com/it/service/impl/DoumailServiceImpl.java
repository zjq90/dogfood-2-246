package com.weibo.service.impl;

import com.weibo.dto.PageResult;
import com.weibo.dto.Result;
import com.weibo.mapper.DoumailMapper;
import com.weibo.model.Doumail;
import com.weibo.service.DoumailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 豆邮服务实现类
 * 实现豆邮相关的业务逻辑
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
        if (userId == null) {
            return Result.error("用户ID不能为空");
        }
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        int offset = (pageNum - 1) * pageSize;
        List<Doumail> list = doumailMapper.selectConversationList(userId, offset, pageSize);
        Long total = doumailMapper.selectConversationCount(userId);

        PageResult<Doumail> pageResult = new PageResult<>(pageNum, pageSize, total, list);
        return Result.success("获取成功", pageResult);
    }

    @Override
    public Result<PageResult<Doumail>> getDoumailDetail(Integer userId, Integer targetUserId, Integer pageNum, Integer pageSize) {
        if (userId == null || targetUserId == null) {
            return Result.error("用户ID不能为空");
        }
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        int offset = (pageNum - 1) * pageSize;
        List<Doumail> list = doumailMapper.selectDoumailDetail(userId, targetUserId, offset, pageSize);
        Long total = doumailMapper.selectDoumailCount(userId, targetUserId);

        // 标记为已读
        doumailMapper.markAsRead(targetUserId, userId);

        PageResult<Doumail> pageResult = new PageResult<>(pageNum, pageSize, total, list);
        return Result.success("获取成功", pageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Doumail> sendDoumail(Doumail doumail) {
        if (doumail == null) {
            return Result.error("豆邮信息不能为空");
        }
        if (doumail.getFromUserId() == null || doumail.getToUserId() == null) {
            return Result.error("发送者或接收者ID不能为空");
        }
        if (!StringUtils.hasText(doumail.getChatMsg())) {
            return Result.error("消息内容不能为空");
        }
        if (doumail.getFromUserId().equals(doumail.getToUserId())) {
            return Result.error("不能给自己发送消息");
        }

        doumail.setChatTime(new Date());
        doumail.setStatus(0);
        doumail.setRead(0);

        int result = doumailMapper.insertDoumail(doumail);
        if (result > 0) {
            logger.info("发送豆邮成功: {} -> {}", doumail.getFromUserId(), doumail.getToUserId());
            return Result.success("发送成功", doumail);
        }

        return Result.error("发送失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> markAsRead(Integer fromUserId, Integer toUserId) {
        if (fromUserId == null || toUserId == null) {
            return Result.error("用户ID不能为空");
        }

        int result = doumailMapper.markAsRead(fromUserId, toUserId);
        if (result >= 0) {
            return Result.success("标记成功", true);
        }

        return Result.error("标记失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteDoumail(Integer doumailId, Integer userId) {
        if (doumailId == null || userId == null) {
            return Result.error("参数不能为空");
        }

        // 查询豆邮信息，判断是发送者还是接收者删除
        // 这里简化处理，假设传入的userId可以判断身份
        int status = 1; // 默认发送者删除

        int result = doumailMapper.deleteDoumail(doumailId, userId, status);
        if (result > 0) {
            logger.info("删除豆邮成功: {} 操作人: {}", doumailId, userId);
            return Result.success("删除成功", true);
        }

        return Result.error("删除失败");
    }

    @Override
    public Result<Integer> getUnreadCount(Integer userId) {
        if (userId == null) {
            return Result.success(0);
        }

        Integer count = doumailMapper.selectUnreadCount(userId);
        return Result.success("获取成功", count != null ? count : 0);
    }
}
