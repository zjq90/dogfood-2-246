package com.it.controller;

import com.it.common.Result;
import com.it.model.ArticleComment;
import com.it.model.ArticleReply;
import com.it.model.User;
import com.it.service.ArticleInteractionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/interaction")
public class ArticleInteractionController {

    @Resource
    private ArticleInteractionService interactionService;

    @PostMapping("/star/{articleId}")
    public Result<String> starArticle(@PathVariable Integer articleId, HttpSession session) {
        log.info("like article, articleId: {}", articleId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("Not logged in");
        }
        boolean success = interactionService.starArticle(user.getUserId(), articleId);
        return success ? Result.success("Like success") : Result.error("Already liked");
    }

    @DeleteMapping("/star/{articleId}")
    public Result<String> unstarArticle(@PathVariable Integer articleId, HttpSession session) {
        log.info("unlike article, articleId: {}", articleId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("Not logged in");
        }
        boolean success = interactionService.unstarArticle(user.getUserId(), articleId);
        return success ? Result.success("Unlike success") : Result.error("Not liked yet");
    }

    @PostMapping("/collect/{articleId}")
    public Result<String> collectArticle(@PathVariable Integer articleId, HttpSession session) {
        log.info("collect article, articleId: {}", articleId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("Not logged in");
        }
        boolean success = interactionService.collectArticle(user.getUserId(), articleId);
        return success ? Result.success("Collect success") : Result.error("Already collected");
    }

    @DeleteMapping("/collect/{articleId}")
    public Result<String> uncollectArticle(@PathVariable Integer articleId, HttpSession session) {
        log.info("uncollect article, articleId: {}", articleId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("Not logged in");
        }
        boolean success = interactionService.uncollectArticle(user.getUserId(), articleId);
        return success ? Result.success("Uncollect success") : Result.error("Not collected yet");
    }

    @PostMapping("/share/{articleId}")
    public Result<String> shareArticle(@PathVariable Integer articleId, HttpSession session) {
        log.info("share article, articleId: {}", articleId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("Not logged in");
        }
        boolean success = interactionService.shareArticle(user.getUserId(), articleId);
        return success ? Result.success("Share success") : Result.error("Already shared");
    }

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

    @GetMapping("/comments/{articleId}")
    public Result<List<ArticleComment>> getComments(@PathVariable Integer articleId) {
        log.info("get comments, articleId: {}", articleId);
        List<ArticleComment> comments = interactionService.getCommentsByArticleId(articleId);
        return Result.success(comments);
    }

    @PostMapping("/comment")
    public Result<ArticleComment> addComment(@RequestBody ArticleComment comment, HttpSession session) {
        log.info("add comment");
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("Not logged in");
        }
        comment.setUserComId(user.getUserId());
        ArticleComment savedComment = interactionService.addComment(comment);
        return Result.success("Comment success", savedComment);
    }

    @DeleteMapping("/comment/{commentId}")
    public Result<String> deleteComment(@PathVariable Integer commentId, HttpSession session) {
        log.info("delete comment, commentId: {}", commentId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("Not logged in");
        }
        boolean success = interactionService.deleteComment(commentId, user.getUserId());
        return success ? Result.success("Delete success") : Result.error("Delete failed");
    }

    @PostMapping("/reply")
    public Result<ArticleReply> addReply(@RequestBody ArticleReply reply, HttpSession session) {
        log.info("add reply");
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("Not logged in");
        }
        reply.setUserId(user.getUserId());
        ArticleReply savedReply = interactionService.addReply(reply);
        return Result.success("Reply success", savedReply);
    }

    @DeleteMapping("/reply/{replyId}")
    public Result<String> deleteReply(@PathVariable Integer replyId, HttpSession session) {
        log.info("delete reply, replyId: {}", replyId);
        User user = (User) session.getAttribute("userInfo");
        if (user == null) {
            return Result.error("Not logged in");
        }
        boolean success = interactionService.deleteReply(replyId, user.getUserId());
        return success ? Result.success("Delete success") : Result.error("Delete failed");
    }

    @GetMapping("/replies/{commentId}")
    public Result<List<ArticleReply>> getRepliesByCommentId(@PathVariable Integer commentId) {
        log.info("get replies, commentId: {}", commentId);
        List<ArticleReply> replies = interactionService.getRepliesByCommentId(commentId);
        return Result.success(replies);
    }

    @GetMapping("/star/users/{articleId}")
    public Result<List<User>> getStarUsers(@PathVariable Integer articleId) {
        log.info("get star users, articleId: {}", articleId);
        List<User> users = interactionService.getStarUsers(articleId);
        return Result.success(users);
    }

    @GetMapping("/collect/users/{articleId}")
    public Result<List<User>> getCollectUsers(@PathVariable Integer articleId) {
        log.info("get collect users, articleId: {}", articleId);
        List<User> users = interactionService.getCollectUsers(articleId);
        return Result.success(users);
    }
}
