package com.it.controller;

import com.it.common.PageResult;
import com.it.common.Result;
import com.it.model.Friend;
import com.it.service.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 濂藉弸鍏崇郴鎺у埗鍣? * 澶勭悊鍏虫敞銆佺矇涓濄€侀粦鍚嶅崟绛夊姛鑳? * 
 * @author weibo Team
 */
@Controller
@RequestMapping("/friend")
public class FriendController {

    private static final Logger logger = LoggerFactory.getLogger(FriendController.class);

    @Autowired
    private FriendService friendService;

    /**
     * 鍏虫敞鐢ㄦ埛
     */
    @PostMapping("/follow/{toUserId}")
    @ResponseBody
    public Result<Boolean> follow(@PathVariable("toUserId") Integer toUserId,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "璇峰厛鐧诲綍");
        }

        return friendService.follow(userId, toUserId);
    }

    /**
     * 鍙栨秷鍏虫敞
     */
    @PostMapping("/unfollow/{toUserId}")
    @ResponseBody
    public Result<Boolean> unfollow(@PathVariable("toUserId") Integer toUserId,
                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "璇峰厛鐧诲綍");
        }

        return friendService.unfollow(userId, toUserId);
    }

    /**
     * 鎷夐粦鐢ㄦ埛
     */
    @PostMapping("/block/{toUserId}")
    @ResponseBody
    public Result<Boolean> block(@PathVariable("toUserId") Integer toUserId,
                                         HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "璇峰厛鐧诲綍");
        }

        return friendService.block(userId, toUserId);
    }

    /**
     * 鍙栨秷鎷夐粦
     */
    @PostMapping("/unblock/{toUserId}")
    @ResponseBody
    public Result<Boolean> unblock(@PathVariable("toUserId") Integer toUserId,
                                           HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "璇峰厛鐧诲綍");
        }

        return friendService.unblock(userId, toUserId);
    }

    /**
     * 鑾峰彇鍏虫敞鍒楄〃
     */
    @GetMapping("/following/list")
    @ResponseBody
    public Result<PageResult<Friend>> getFollowingList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "璇峰厛鐧诲綍");
        }

        return friendService.getFollowingList(userId, pageNum, pageSize);
    }

    /**
     * 鑾峰彇绮変笣鍒楄〃
     */
    @GetMapping("/follower/list")
    @ResponseBody
    public Result<PageResult<Friend>> getFollowerList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                   HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "璇峰厛鐧诲綍");
        }

        return friendService.getFollowerList(userId, pageNum, pageSize);
    }

    /**
     * 鑾峰彇濂藉弸鍒楄〃
     */
    @GetMapping("/friend/list")
    @ResponseBody
    public Result<PageResult<Friend>> getFriendList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                 HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "璇峰厛鐧诲綍");
        }

        return friendService.getFriendList(userId, pageNum, pageSize);
    }

    /**
     * 鑾峰彇榛戝悕鍗曞垪琛?     */
    @GetMapping("/blacklist")
    @ResponseBody
    public Result<List<Friend>> getBlacklist(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "璇峰厛鐧诲綍");
        }

        return friendService.getBlacklist(userId);
    }

    /**
     * 妫€鏌ユ槸鍚﹀凡鍏虫敞
     */
    @GetMapping("/checkFollowing/{toUserId}")
    @ResponseBody
    public Result<Boolean> checkFollowing(@PathVariable("toUserId") Integer toUserId,
                                               HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.success(false);
        }
        return friendService.isFollowing(userId, toUserId);
    }

    /**
     * 鑾峰彇鍏虫敞鏁般€佺矇涓濇暟銆佸ソ鍙嬫暟
     */
    @GetMapping("/stats")
    @ResponseBody
    public Result<java.util.Map<String, Integer>> getUserStats(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "璇峰厛鐧诲綍");
        }

        java.util.Map<String, Integer> stats = new java.util.HashMap<>();
        stats.put("followingCount", friendService.getFollowingCount(userId).getData());
        stats.put("followerCount", friendService.getFollowerCount(userId).getData());
        stats.put("friendCount", friendService.getFriendCount(userId).getData());
        return Result.success(stats);
    }
}

