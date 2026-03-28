package com.weibo.service.impl;

import com.weibo.dto.PageResult;
import com.weibo.dto.Result;
import com.weibo.mapper.ArticleInteractionMapper;
import com.weibo.mapper.ArticleMapper;
import com.weibo.model.Article;
import com.weibo.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 文章服务实现类
 * 实现文章相关的业务逻辑
 *
 * @author weibo Team
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleInteractionMapper articleInteractionMapper;

    @Override
    public Result<Article> getArticleDetail(Integer articleId, Integer userId) {
        if (articleId == null) {
            return Result.error("文章ID不能为空");
        }

        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            return Result.error("文章不存在");
        }

        // 如果传入了用户ID，检查是否点赞/收藏
        if (userId != null) {
            int starCount = articleInteractionMapper.checkArticleStar(userId, articleId);
            int collectionCount = articleInteractionMapper.checkArticleCollection(userId, articleId);
            article.setHasStarred(starCount > 0);
            article.setHasCollected(collectionCount > 0);
        }

        return Result.success("获取成功", article);
    }

    @Override
    public Result<PageResult<Article>> getArticleList(Integer pageNum, Integer pageSize, Integer tagId, String keyword) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        int offset = (pageNum - 1) * pageSize;
        List<Article> list = articleMapper.selectArticleList(offset, pageSize, tagId, keyword);
        Long total = articleMapper.selectArticleCount(tagId, keyword);

        PageResult<Article> pageResult = new PageResult<>(pageNum, pageSize, total, list);
        return Result.success("获取成功", pageResult);
    }

    @Override
    public Result<PageResult<Article>> getUserArticles(Integer userId, Integer pageNum, Integer pageSize) {
        if (userId == null) {
            return Result.error("用户ID不能为空");
        }
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        int offset = (pageNum - 1) * pageSize;
        List<Article> list = articleMapper.selectByUserId(userId, offset, pageSize);
        Long total = articleMapper.selectCountByUserId(userId);

        PageResult<Article> pageResult = new PageResult<>(pageNum, pageSize, total, list);
        return Result.success("获取成功", pageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Article> publishArticle(Article article, List<Integer> tagIds) {
        if (article == null) {
            return Result.error("文章信息不能为空");
        }
        if (!StringUtils.hasText(article.getTitle())) {
            return Result.error("文章标题不能为空");
        }
        if (!StringUtils.hasText(article.getContent())) {
            return Result.error("文章内容不能为空");
        }
        if (article.getUserId() == null) {
            return Result.error("发布者ID不能为空");
        }

        article.setPublishedTime(new Date());
        article.setStar(0);
        article.setCollection(0);
        article.setComment(0);
        article.setShare(0);
        article.setPageView(0);
        article.setStick(0);

        int result = articleMapper.insertArticle(article);
        if (result <= 0) {
            return Result.error("发布失败");
        }

        // 处理标签关联
        if (!CollectionUtils.isEmpty(tagIds)) {
            for (Integer tagId : tagIds) {
                articleMapper.insertArticleTag(article.getArticleId(), tagId);
            }
        }

        logger.info("发布文章成功: {} 作者: {}", article.getArticleId(), article.getUserId());
        return Result.success("发布成功", article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Article> updateArticle(Article article, List<Integer> tagIds) {
        if (article == null || article.getArticleId() == null) {
            return Result.error("文章ID不能为空");
        }

        int result = articleMapper.updateArticle(article);
        if (result <= 0) {
            return Result.error("更新失败");
        }

        // 更新标签关联
        if (tagIds != null) {
            articleMapper.deleteArticleTags(article.getArticleId());
            if (!CollectionUtils.isEmpty(tagIds)) {
                for (Integer tagId : tagIds) {
                    articleMapper.insertArticleTag(article.getArticleId(), tagId);
                }
            }
        }

        logger.info("更新文章成功: {}", article.getArticleId());
        return Result.success("更新成功", article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteArticle(Integer articleId, Integer userId) {
        if (articleId == null) {
            return Result.error("文章ID不能为空");
        }

        int result = articleMapper.deleteById(articleId);
        if (result > 0) {
            logger.info("删除文章成功: {} 操作人: {}", articleId, userId);
            return Result.success("删除成功", true);
        }

        return Result.error("删除失败");
    }

    @Override
    public Result<List<Article>> getHotArticles(Integer limit) {
        if (limit == null || limit < 1) {
            limit = 10;
        }
        List<Article> list = articleMapper.selectHotArticles(limit);
        return Result.success("获取成功", list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> incrementPageView(Integer articleId) {
        if (articleId == null) {
            return Result.error("文章ID不能为空");
        }

        int result = articleMapper.incrementPageView(articleId);
        if (result > 0) {
            return Result.success("操作成功", true);
        }

        return Result.error("操作失败");
    }
}
