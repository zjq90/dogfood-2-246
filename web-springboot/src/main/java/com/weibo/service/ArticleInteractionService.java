package com.weibo.service;

import com.weibo.mapper.ArticleCommentMapper;
import com.weibo.mapper.ArticleInteractionMapper;
import com.weibo.mapper.ArticleMapper;
import com.weibo.model.ArticleComment;
import com.weibo.model.ArticleReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ArticleInteractionService {

    @Resource
    private ArticleInteractionMapper interactionMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleCommentMapper commentMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean starArticle(Integer userId, Integer articleId) {
        log.info("点赞文章, userId: {}, articleId: {}", userId, articleId);
        int count = interactionMapper.checkArticleStar(userId, articleId);
        if (count > 0) {
            log.warn("已点赞过该文章");
            return false;
        }
        interactionMapper.insertArticleStar(userId, articleId);
        articleMapper.updateStarNum(articleId, 1);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean unstarArticle(Integer userId, Integer articleId) {
        log.info("取消点赞文章, userId: {}, articleId: {}", userId, articleId);
        int count = interactionMapper.checkArticleStar(userId, articleId);
        if (count == 0) {
            log.warn("未点赞过该文章");
            return false;
        }
        interactionMapper.deleteArticleStar(userId, articleId);
        articleMapper.updateStarNum(articleId, -1);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean collectArticle(Integer userId, Integer articleId) {
        log.info("收藏文章, userId: {}, articleId: {}", userId, articleId);
        int count = interactionMapper.checkArticleCollection(userId, articleId);
        if (count > 0) {
            log.warn("已收藏过该文章");
            return false;
        }
        interactionMapper.insertArticleCollection(userId, articleId);
        articleMapper.updateCollectionNum(articleId, 1);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean uncollectArticle(Integer userId, Integer articleId) {
        log.info("取消收藏文章, userId: {}, articleId: {}", userId, articleId);
        int count = interactionMapper.checkArticleCollection(userId, articleId);
        if (count == 0) {
            log.warn("未收藏过该文章");
            return false;
        }
        interactionMapper.deleteArticleCollection(userId, articleId);
        articleMapper.updateCollectionNum(articleId, -1);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean shareArticle(Integer userId, Integer articleId) {
        log.info("转发文章, userId: {}, articleId: {}", userId, articleId);
        int count = interactionMapper.checkArticleShare(userId, articleId);
        if (count > 0) {
            log.warn("已转发过该文章");
            return false;
        }
        interactionMapper.insertArticleShare(userId, articleId);
        articleMapper.updateShareNum(articleId, 1);
        return true;
    }

    public boolean isStarred(Integer userId, Integer articleId) {
        return interactionMapper.checkArticleStar(userId, articleId) > 0;
    }

    public boolean isCollected(Integer userId, Integer articleId) {
        return interactionMapper.checkArticleCollection(userId, articleId) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public ArticleComment addComment(ArticleComment comment) {
        log.info("发表评论, articleId: {}, userId: {}", comment.getArticleId(), comment.getUserComId());
        commentMapper.insert(comment);
        articleMapper.updateCommentNum(comment.getArticleId(), 1);
        return comment;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteComment(Integer commentId, Integer userId) {
        log.info("删除评论, commentId: {}, userId: {}", commentId, userId);
        int result = commentMapper.delete(commentId);
        return result > 0;
    }

    public List<ArticleComment> getCommentsByArticleId(Integer articleId) {
        log.debug("获取文章评论列表, articleId: {}", articleId);
        return commentMapper.selectByArticleId(articleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean starComment(Integer userId, Integer commentId) {
        log.info("点赞评论, userId: {}, commentId: {}", userId, commentId);
        int count = commentMapper.checkCommentStar(userId, commentId);
        if (count > 0) {
            return false;
        }
        commentMapper.insertCommentStar(userId, commentId);
        commentMapper.updateStarNum(commentId, 1);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean unstarComment(Integer userId, Integer commentId) {
        log.info("取消点赞评论, userId: {}, commentId: {}", userId, commentId);
        int count = commentMapper.checkCommentStar(userId, commentId);
        if (count == 0) {
            return false;
        }
        commentMapper.deleteCommentStar(userId, commentId);
        commentMapper.updateStarNum(commentId, -1);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public ArticleReply addReply(ArticleReply reply) {
        log.info("发表回复, commentId: {}", reply.getCommentId());
        commentMapper.insertReply(reply);
        return reply;
    }

    public List<ArticleReply> getRepliesByCommentId(Integer commentId) {
        log.debug("获取评论回复列表, commentId: {}", commentId);
        return commentMapper.selectReplyByCommentId(commentId);
    }
}
