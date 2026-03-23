package com.weibo.controller;

import com.weibo.common.Result;
import com.weibo.dto.PageResult;
import com.weibo.model.Friend;
import com.weibo.service.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/friend")
public class FriendController {

    private static final Logger logger = LoggerFactory.getLogger(FriendController.class);

    @Autowired
    private FriendService friendService;

    @PostMapping("/follow/{toUserId}")
    @ResponseBody
    public Result<Boolean> follow(@PathVariable("toUserId") Integer toUserId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        return friendService.follow(userId, toUserId);
    }

    @PostMapping("/unfollow/{toUserId}")
    @ResponseBody
    public Result<Boolean> unfollow(@PathVariable("toUserId") Integer toUserId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        return friendService.unfollow(userId, toUserId);
    }

    @PostMapping("/block/{toUserId}")
    @ResponseBody
    public Result<Boolean> block(@PathVariable("toUserId") Integer toUserId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        return friendService.block(userId, toUserId);
    }

    @PostMapping("/unblock/{toUserId}")
    @ResponseBody
    public Result<Boolean> unblock(@PathVariable("toUserId") Integer toUserId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        return friendService.unblock(userId, toUserId);
    }

    @GetMapping("/following/list")
    @ResponseBody
    public Result<PageResult<Friend>> getFollowingList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                       HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        return friendService.getFollowingList(userId, pageNum, pageSize);
    }

    @GetMapping("/follower/list")
    @ResponseBody
    public Result<PageResult<Friend>> getFollowerList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                      HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        return friendService.getFollowerList(userId, pageNum, pageSize);
    }

    @GetMapping("/friend/list")
    @ResponseBody
    public Result<PageResult<Friend>> getFriendList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        return friendService.getFriendList(userId, pageNum, pageSize);
    }

    @GetMapping("/blacklist")
    @ResponseBody
    public Result<List<Friend>> getBlacklist(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        return friendService.getBlacklist(userId);
    }

    @GetMapping("/checkFollowing/{toUserId}")
    @ResponseBody
    public Result<Boolean> checkFollowing(@PathVariable("toUserId") Integer toUserId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.success(false);
        }
        return friendService.isFollowing(userId, toUserId);
    }

    @GetMapping("/stats")
    @ResponseBody
    public Result<java.util.Map<String, Integer>> getUserStats(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        java.util.Map<String, Integer> stats = new java.util.HashMap<>();
        stats.put("followingCount", friendService.getFollowingCount(userId).getData());
        stats.put("followerCount", friendService.getFollowerCount(userId).getData());
        stats.put("friendCount", friendService.getFriendCount(userId).getData());
        return Result.success(stats);
    }
}
