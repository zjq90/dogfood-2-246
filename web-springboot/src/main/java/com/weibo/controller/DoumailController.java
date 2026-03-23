package com.weibo.controller;

import com.weibo.common.Result;
import com.weibo.dto.PageResult;
import com.weibo.model.Doumail;
import com.weibo.service.DoumailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/doumail")
public class DoumailController {

    private static final Logger logger = LoggerFactory.getLogger(DoumailController.class);

    @Autowired
    private DoumailService doumailService;

    @GetMapping("/conversation/list")
    @ResponseBody
    public Result<PageResult<Doumail>> getConversationList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                           HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        return doumailService.getConversationList(userId, pageNum, pageSize);
    }

    @GetMapping("/detail/{targetUserId}")
    @ResponseBody
    public Result<PageResult<Doumail>> getDoumailDetail(@PathVariable("targetUserId") Integer targetUserId,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        doumailService.markAsRead(targetUserId, userId);
        return doumailService.getDoumailDetail(userId, targetUserId, pageNum, pageSize);
    }

    @PostMapping("/send")
    @ResponseBody
    public Result<Doumail> sendDoumail(@RequestParam("toUserId") Integer toUserId,
                                       @RequestParam("content") String content,
                                       HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        if (content == null || content.trim().isEmpty()) {
            return Result.fail("内容不能为空");
        }
        Doumail doumail = new Doumail();
        doumail.setFromUserId(userId);
        doumail.setToUserId(toUserId);
        doumail.setChatMsg(content);
        return doumailService.sendDoumail(doumail);
    }

    @PostMapping("/delete/{doumailId}")
    @ResponseBody
    public Result<Boolean> deleteDoumail(@PathVariable("doumailId") Integer doumailId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        return doumailService.deleteDoumail(doumailId, userId);
    }

    @GetMapping("/unread/count")
    @ResponseBody
    public Result<Integer> getUnreadCount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.success(0);
        }
        return doumailService.getUnreadCount(userId);
    }
}
