package com.it.service;

import com.it.common.Result;
import com.it.model.ArticleComment;
import com.it.model.ArticleReply;
import com.it.model.User;

import java.util.List;

public interface ArticleInteractionService {

    boolean starArticle(Integer userId, Integer articleId);

    boolean unstarArticle(Integer userId, Integer articleId);

    boolean collectArticle(Integer userId, Integer articleId);

    boolean uncollectArticle(Integer userId, Integer articleId);

    boolean shareArticle(Integer userId, Integer articleId);

    boolean isStarred(Integer userId, Integer articleId);

    boolean isCollected(Integer userId, Integer articleId);

    List<ArticleComment> getCommentsByArticleId(Integer articleId);

    ArticleComment addComment(ArticleComment comment);

    boolean deleteComment(Integer commentId, Integer userId);

    ArticleReply addReply(ArticleReply reply);

    boolean deleteReply(Integer replyId, Integer userId);

    List<ArticleReply> getRepliesByCommentId(Integer commentId);

    List<User> getStarUsers(Integer articleId);

    List<User> getCollectUsers(Integer articleId);
}
