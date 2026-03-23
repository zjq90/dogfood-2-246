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
import java.util.List;

@Slf4j
@Service
public class DoumailServiceImpl implements DoumailService {

    @Resource
    private DoumailMapper doumailMapper;

    @Override
    public Result<PageResult<Doumail>> getConversationList(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("get conversation list, userId: {}, pageNum: {}, pageSize: {}", userId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Doumail> conversations = doumailMapper.selectConversationList(userId, offset, pageSize);
        Long total = doumailMapper.selectConversationCount(userId);
        PageResult<Doumail> pageResult = PageResult.of(pageNum, pageSize, total, conversations);
        return Result.success(pageResult);
    }

    @Override
    public Result<PageResult<Doumail>> getDoumailDetail(Integer userId, Integer targetUserId, Integer pageNum, Integer pageSize) {
        log.info("get doumail detail, userId: {}, targetUserId: {}, pageNum: {}, pageSize: {}", userId, targetUserId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Doumail> doumails = doumailMapper.selectDoumailDetail(userId, targetUserId, offset, pageSize);
        Long total = doumailMapper.selectDoumailCount(userId, targetUserId);
        PageResult<Doumail> pageResult = PageResult.of(pageNum, pageSize, total, doumails);
        return Result.success(pageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Doumail> sendDoumail(Doumail doumail) {
        log.info("send doumail, fromUserId: {}, toUserId: {}", doumail.getFromUserId(), doumail.getToUserId());
        if (doumail.getFromUserId().equals(doumail.getToUserId())) {
            return Result.error("Cannot send doumail to yourself");
        }
        int result = doumailMapper.insertDoumail(doumail);
        return result > 0 ? Result.success(doumail) : Result.error("Send failed");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> markAsRead(Integer fromUserId, Integer toUserId) {
        log.info("mark as read, fromUserId: {}, toUserId: {}", fromUserId, toUserId);
        int result = doumailMapper.markAsRead(fromUserId, toUserId);
        return Result.success(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteDoumail(Integer doumailId, Integer userId) {
        log.info("delete doumail, doumailId: {}, userId: {}", doumailId, userId);
        int result = doumailMapper.deleteDoumail(doumailId, userId, 1);
        if (result == 0) {
            result = doumailMapper.deleteDoumail(doumailId, userId, 2);
        }
        return result > 0 ? Result.success(true) : Result.error("Delete failed");
    }

    @Override
    public Result<Integer> getUnreadCount(Integer userId) {
        Integer count = doumailMapper.selectUnreadCount(userId);
        return Result.success(count != null ? count : 0);
    }
}
