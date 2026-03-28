package com.it.service;

import com.it.common.Result;
import com.it.mapper.ArticleCommentMapper;
import com.it.mapper.ArticleInteractionMapper;
import com.it.mapper.ArticleMapper;
import com.it.model.ArticleComment;
import com.it.model.ArticleReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章互动服务类
 * 处理点赞、收藏、评论等互动操作
 */
@Slf4j
@Service
public class ArticleInteractionService {

    @Resource
    private ArticleInteractionMapper interactionMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleCommentMapper commentMapper;

    /**
     * 点赞文章
     */
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

    /**
     * 取消点赞文章
     */
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

    /**
     * 收藏文章
     */
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

    /**
     * 取消收藏文章
     */
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

    /**
     * 转发文章
     */
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

    /**
     * 检查用户是否点赞文章
     */
    public boolean isStarred(Integer userId, Integer articleId) {
        return interactionMapper.checkArticleStar(userId, articleId) > 0;
    }

    /**
     * 检查用户是否收藏文章
     */
    public boolean isCollected(Integer userId, Integer articleId) {
        return interactionMapper.checkArticleCollection(userId, articleId) > 0;
    }

    /**
     * 发表评论
     */
    @Transactional(rollbackFor = Exception.class)
    public ArticleComment addComment(ArticleComment comment) {
        log.info("发表评论, articleId: {}, userId: {}", comment.getArticleId(), comment.getUserComId());
        commentMapper.insert(comment);
        articleMapper.updateCommentNum(comment.getArticleId(), 1);
        return comment;
    }

    /**
     * 删除评论
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteComment(Integer commentId, Integer userId) {
        log.info("删除评论, commentId: {}, userId: {}", commentId, userId);
        int result = commentMapper.delete(commentId);
        return result > 0;
    }

    /**
     * 获取文章评论列表
     */
    public List<ArticleComment> getCommentsByArticleId(Integer articleId) {
        log.debug("获取文章评论列表, articleId: {}", articleId);
        return commentMapper.selectByArticleId(articleId);
    }

    /**
     * 点赞评论
     */
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

    /**
     * 取消点赞评论
     */
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

    /**
     * 发表回复
     */
    @Transactional(rollbackFor = Exception.class)
    public ArticleReply addReply(ArticleReply reply) {
        log.info("发表回复, commentId: {}", reply.getCommentId());
        commentMapper.insertReply(reply);
        return reply;
    }

    /**
     * 获取评论的回复列表
     */
    public List<ArticleReply> getRepliesByCommentId(Integer commentId) {
        log.debug("获取评论回复列表, commentId: {}", commentId);
        return commentMapper.selectReplyByCommentId(commentId);
    }
}
