package com.weibo.controller;

import com.weibo.dto.Result;
import com.weibo.model.ArticleComment;
import com.weibo.model.ArticleReply;
import com.weibo.model.User;
import com.weibo.service.ArticleInteractionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 文章互动控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/interaction")
public class ArticleInteractionController {

    @Resource
    private ArticleInteractionService interactionService;

    /**
     * 点赞文章
     */
    @PostMapping("/star/{articleId}")
    public Result<Void> starArticle(@PathVariable Integer articleId, HttpSession session) {
        log.info("点赞文章, articleId: {}", articleId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("未登录");
        }
        boolean success = interactionService.starArticle(user.getUserId(), articleId);
        return success ? Result.success("点赞成功") : Result.error("已点赞过");
    }

    /**
     * 取消点赞文章
     */
    @DeleteMapping("/star/{articleId}")
    public Result<Void> unstarArticle(@PathVariable Integer articleId, HttpSession session) {
        log.info("取消点赞文章, articleId: {}", articleId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("未登录");
        }
        boolean success = interactionService.unstarArticle(user.getUserId(), articleId);
        return success ? Result.success("取消点赞成功") : Result.error("未点赞过");
    }

    /**
     * 收藏文章
     */
    @PostMapping("/collect/{articleId}")
    public Result<Void> collectArticle(@PathVariable Integer articleId, HttpSession session) {
        log.info("收藏文章, articleId: {}", articleId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("未登录");
        }
        boolean success = interactionService.collectArticle(user.getUserId(), articleId);
        return success ? Result.success("收藏成功") : Result.error("已收藏过");
    }

    /**
     * 取消收藏文章
     */
    @DeleteMapping("/collect/{articleId}")
    public Result<Void> uncollectArticle(@PathVariable Integer articleId, HttpSession session) {
        log.info("取消收藏文章, articleId: {}", articleId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("未登录");
        }
        boolean success = interactionService.uncollectArticle(user.getUserId(), articleId);
        return success ? Result.success("取消收藏成功") : Result.error("未收藏过");
    }

    /**
     * 转发文章
     */
    @PostMapping("/share/{articleId}")
    public Result<Void> shareArticle(@PathVariable Integer articleId, HttpSession session) {
        log.info("转发文章, articleId: {}", articleId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("未登录");
        }
        boolean success = interactionService.shareArticle(user.getUserId(), articleId);
        return success ? Result.success("转发成功") : Result.error("已转发过");
    }

    /**
     * 检查点赞和收藏状态
     */
    @GetMapping("/status/{articleId}")
    public Result<int[]> getInteractionStatus(@PathVariable Integer articleId, HttpSession session) {
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.success(new int[]{0, 0});
        }
        boolean starred = interactionService.isStarred(user.getUserId(), articleId);
        boolean collected = interactionService.isCollected(user.getUserId(), articleId);
        return Result.success(new int[]{starred ? 1 : 0, collected ? 1 : 0});
    }

    /**
     * 获取文章评论列表
     */
    @GetMapping("/comments/{articleId}")
    public Result<List<ArticleComment>> getComments(@PathVariable Integer articleId) {
        log.info("获取文章评论列表, articleId: {}", articleId);
        List<ArticleComment> comments = interactionService.getCommentsByArticleId(articleId);
        return Result.success(comments);
    }

    /**
     * 发表评论
     */
    @PostMapping("/comment")
    public Result<ArticleComment> addComment(@RequestBody ArticleComment comment, HttpSession session) {
        log.info("发表评论");
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("未登录");
        }
        comment.setUserComId(user.getUserId());
        ArticleComment savedComment = interactionService.addComment(comment);
        return Result.success("评论成功", savedComment);
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/comment/{commentId}")
    public Result<Void> deleteComment(@PathVariable Integer commentId, HttpSession session) {
        log.info("删除评论, commentId: {}", commentId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("未登录");
        }
        boolean success = interactionService.deleteComment(commentId, user.getUserId());
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }

    /**
     * 点赞评论
     */
    @PostMapping("/comment/star/{commentId}")
    public Result<Void> starComment(@PathVariable Integer commentId, HttpSession session) {
        log.info("点赞评论, commentId: {}", commentId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("未登录");
        }
        boolean success = interactionService.starComment(user.getUserId(), commentId);
        return success ? Result.success("点赞成功") : Result.error("已点赞过");
    }

    /**
     * 获取评论的回复列表
     */
    @GetMapping("/reply/{commentId}")
    public Result<List<ArticleReply>> getReplies(@PathVariable Integer commentId) {
        log.info("获取评论回复列表, commentId: {}", commentId);
        List<ArticleReply> replies = interactionService.getRepliesByCommentId(commentId);
        return Result.success(replies);
    }

    /**
     * 发表回复
     */
    @PostMapping("/reply")
    public Result<ArticleReply> addReply(@RequestBody ArticleReply reply, HttpSession session) {
        log.info("发表回复");
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("未登录");
        }
        reply.setUserReplyFromId(user.getUserId());
        ArticleReply savedReply = interactionService.addReply(reply);
        return Result.success("回复成功", savedReply);
    }
}
