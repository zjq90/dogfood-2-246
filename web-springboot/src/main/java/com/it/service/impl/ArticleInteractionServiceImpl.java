package com.it.service.impl;

import com.it.mapper.ArticleInteractionMapper;
import com.it.model.ArticleComment;
import com.it.model.ArticleReply;
import com.it.model.ArticleStar;
import com.it.model.ArticleCollection;
import com.it.model.User;
import com.it.service.ArticleInteractionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ArticleInteractionServiceImpl implements ArticleInteractionService {

    @Resource
    private ArticleInteractionMapper interactionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean starArticle(Integer userId, Integer articleId) {
        log.info("star article, userId: {}, articleId: {}", userId, articleId);
        if (interactionMapper.checkStarExists(userId, articleId) > 0) {
            log.warn("user {} already starred article {}", userId, articleId);
            return false;
        }
        ArticleStar star = new ArticleStar();
        star.setUserId(userId);
        star.setArticleId(articleId);
        star.setCreateTime(new Date());
        int result = interactionMapper.insertStar(star);
        if (result > 0) {
            interactionMapper.incrementStarCount(articleId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unstarArticle(Integer userId, Integer articleId) {
        log.info("unstar article, userId: {}, articleId: {}", userId, articleId);
        ArticleStar star = interactionMapper.selectStar(userId, articleId, 1);
        if (star == null) {
            log.warn("user {} has not starred article {}", userId, articleId);
            return false;
        }
        int result = interactionMapper.deleteStar(star.getStarId());
        if (result > 0) {
            interactionMapper.decrementStarCount(articleId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean collectArticle(Integer userId, Integer articleId) {
        log.info("collect article, userId: {}, articleId: {}", userId, articleId);
        if (interactionMapper.checkCollectExists(userId, articleId) > 0) {
            log.warn("user {} already collected article {}", userId, articleId);
            return false;
        }
        ArticleCollection collection = new ArticleCollection();
        collection.setUserId(userId);
        collection.setArticleId(articleId);
        collection.setCreateTime(new Date());
        int result = interactionMapper.insertCollection(collection);
        if (result > 0) {
            interactionMapper.incrementCollectCount(articleId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean uncollectArticle(Integer userId, Integer articleId) {
        log.info("uncollect article, userId: {}, articleId: {}", userId, articleId);
        ArticleCollection collection = interactionMapper.selectCollection(userId, articleId);
        if (collection == null) {
            log.warn("user {} has not collected article {}", userId, articleId);
            return false;
        }
        int result = interactionMapper.deleteCollection(collection.getCollectionId());
        if (result > 0) {
            interactionMapper.decrementCollectCount(articleId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean shareArticle(Integer userId, Integer articleId) {
        log.info("share article, userId: {}, articleId: {}", userId, articleId);
        int result = interactionMapper.incrementShareCount(articleId);
        return result > 0;
    }

    @Override
    public boolean isStarred(Integer userId, Integer articleId) {
        return interactionMapper.checkStarExists(userId, articleId) > 0;
    }

    @Override
    public boolean isCollected(Integer userId, Integer articleId) {
        return interactionMapper.checkCollectExists(userId, articleId) > 0;
    }

    @Override
    public List<ArticleComment> getCommentsByArticleId(Integer articleId) {
        log.info("get comments by articleId: {}", articleId);
        return interactionMapper.selectCommentsByArticleId(articleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleComment addComment(ArticleComment comment) {
        log.info("add comment, articleId: {}, userId: {}", comment.getArticleId(), comment.getUserComId());
        comment.setCreateTime(new Date());
        comment.setComTime(new Date());
        int result = interactionMapper.insertComment(comment);
        if (result > 0) {
            interactionMapper.incrementCommentCount(comment.getArticleId());
            return comment;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteComment(Integer commentId, Integer userId) {
        log.info("delete comment, commentId: {}, userId: {}", commentId, userId);
        List<ArticleComment> comments = interactionMapper.selectCommentsByArticleId(0);
        Integer articleId = null;
        if (!comments.isEmpty()) {
            articleId = comments.get(0).getArticleId();
        }
        int result = interactionMapper.deleteComment(commentId, userId);
        if (result > 0 && articleId != null) {
            interactionMapper.decrementCommentCount(articleId);
        }
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleReply addReply(ArticleReply reply) {
        log.info("add reply, commentId: {}, userId: {}", reply.getCommentId(), reply.getUserReplyFromId());
        reply.setCreateTime(new Date());
        reply.setReplyTime(new Date());
        int result = interactionMapper.insertReply(reply);
        return result > 0 ? reply : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteReply(Integer replyId, Integer userId) {
        log.info("delete reply, replyId: {}, userId: {}", replyId, userId);
        int result = interactionMapper.deleteReply(replyId, userId);
        return result > 0;
    }

    @Override
    public List<ArticleReply> getRepliesByCommentId(Integer commentId) {
        log.info("get replies by commentId: {}", commentId);
        return interactionMapper.selectRepliesByCommentId(commentId);
    }

    @Override
    public List<User> getStarUsers(Integer articleId) {
        log.info("get star users, articleId: {}", articleId);
        return interactionMapper.selectStarUsers(articleId);
    }

    @Override
    public List<User> getCollectUsers(Integer articleId) {
        log.info("get collect users, articleId: {}", articleId);
        return interactionMapper.selectCollectUsers(articleId);
    }
}
