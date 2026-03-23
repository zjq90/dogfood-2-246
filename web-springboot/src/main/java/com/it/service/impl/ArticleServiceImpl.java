package com.it.service.impl;

import com.it.common.PageResult;
import com.it.common.Result;
import com.it.mapper.ArticleMapper;
import com.it.model.Article;
import com.it.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Result<Article> getArticleDetail(Integer articleId, Integer userId) {
        log.info("get article detail, articleId: {}, userId: {}", articleId, userId);
        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            return Result.error("Article not found");
        }
        return Result.success(article);
    }

    @Override
    public Result<PageResult<Article>> getArticleList(Integer pageNum, Integer pageSize, Integer tagId, String keyword) {
        log.info("get article list, pageNum: {}, pageSize: {}, tagId: {}, keyword: {}", pageNum, pageSize, tagId, keyword);
        int offset = (pageNum - 1) * pageSize;
        List<Article> articles = articleMapper.selectArticleList(offset, pageSize, tagId, keyword);
        Long count = articleMapper.selectArticleCount(tagId, keyword);
        PageResult<Article> pageResult = PageResult.of(pageNum, pageSize, count, articles);
        return Result.success(pageResult);
    }

    @Override
    public Result<PageResult<Article>> getUserArticles(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("get user articles, userId: {}, pageNum: {}, pageSize: {}", userId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Article> articles = articleMapper.selectByUserId(userId, offset, pageSize);
        Long count = articleMapper.selectCountByUserId(userId);
        PageResult<Article> pageResult = PageResult.of(pageNum, pageSize, count, articles);
        return Result.success(pageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Article> publishArticle(Article article, List<Integer> tagIds) {
        log.info("publish article, userId: {}", article.getAuthorId());
        article.setPublishedTime(new Date());
        article.setUpdateTime(new Date());
        article.setStarNum(0);
        article.setCommentNum(0);
        article.setShareNum(0);
        article.setCollectionNum(0);
        article.setCollectCount(0);
        article.setPageView(0);
        int result = articleMapper.insertArticle(article);
        if (result > 0) {
            if (tagIds != null && !tagIds.isEmpty()) {
                for (Integer tagId : tagIds) {
                    articleMapper.insertArticleTag(article.getArticleId(), tagId);
                }
            }
            return Result.success("Publish success", article);
        }
        return Result.error("Publish failed");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Article> updateArticle(Article article, List<Integer> tagIds) {
        log.info("update article, articleId: {}", article.getArticleId());
        article.setUpdateTime(new Date());
        int result = articleMapper.updateArticle(article);
        if (result > 0) {
            if (tagIds != null && !tagIds.isEmpty()) {
                articleMapper.deleteArticleTags(article.getArticleId());
                for (Integer tagId : tagIds) {
                    articleMapper.insertArticleTag(article.getArticleId(), tagId);
                }
            }
            Article updatedArticle = articleMapper.selectById(article.getArticleId());
            return Result.success("Update success", updatedArticle);
        }
        return Result.error("Update failed");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteArticle(Integer articleId, Integer userId) {
        log.info("delete article, articleId: {}, userId: {}", articleId, userId);
        articleMapper.deleteArticleTags(articleId);
        int result = articleMapper.deleteById(articleId);
        return result > 0 ? Result.success("Delete success", true) : Result.error("Delete failed");
    }

    @Override
    public Result<List<Article>> getHotArticles(Integer limit) {
        log.info("get hot articles, limit: {}", limit);
        List<Article> articles = articleMapper.selectHotArticles(limit);
        return Result.success(articles);
    }

    @Override
    public Result<Boolean> incrementPageView(Integer articleId) {
        log.info("increment page view, articleId: {}", articleId);
        int result = articleMapper.incrementPageView(articleId);
        return result > 0 ? Result.success(true) : Result.error("Increment failed");
    }
}
