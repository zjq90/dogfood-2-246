package com.weibo.service.impl;

import com.weibo.common.Result;
import com.weibo.mapper.ArticleMapper;
import com.weibo.model.Article;
import com.weibo.model.ArticleList;
import com.weibo.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result<Article> getArticleDetail(Integer articleId, Integer userId) {
        try {
            Article article = articleMapper.selectByArticleId(articleId);
            if (article == null) {
                return Result.error("文章不存在");
            }
            return Result.success(article);
        } catch (Exception e) {
            logger.error("获取文章详情异常", e);
            return Result.error("获取文章详情异常：" + e.getMessage());
        }
    }

    @Override
    public Result<ArticleList> getArticleList(Integer pageNum, Integer pageSize, Integer tagId, String keyword) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List<Article> articles;
            int total;
            
            if (keyword != null && !keyword.isEmpty()) {
                articles = articleMapper.selectSearchByPage(keyword, offset, pageSize);
                total = articleMapper.selectSearchCount(keyword);
            } else {
                articles = articleMapper.selectArticleListByPage(offset, pageSize);
                total = articleMapper.selectArticleCount();
            }
            
            ArticleList articleList = new ArticleList();
            articleList.setList(articles);
            articleList.setTotal(total);
            articleList.setPageNum(pageNum);
            articleList.setPageSize(pageSize);
            
            return Result.success(articleList);
        } catch (Exception e) {
            logger.error("获取文章列表异常", e);
            return Result.error("获取文章列表异常：" + e.getMessage());
        }
    }

    @Override
    public Result<ArticleList> getUserArticles(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List<Article> articles = articleMapper.selectByAuthorId(userId, offset, pageSize);
            int total = articleMapper.selectCountByAuthorId(userId);
            
            ArticleList articleList = new ArticleList();
            articleList.setList(articles);
            articleList.setTotal(total);
            articleList.setPageNum(pageNum);
            articleList.setPageSize(pageSize);
            
            return Result.success(articleList);
        } catch (Exception e) {
            logger.error("获取用户文章列表异常", e);
            return Result.error("获取用户文章列表异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Article> publishArticle(Article article, List<Integer> tagIds) {
        try {
            int result = articleMapper.insert(article);
            if (result > 0) {
                return Result.success(article);
            }
            return Result.error("发布文章失败");
        } catch (Exception e) {
            logger.error("发布文章异常", e);
            return Result.error("发布文章异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Article> updateArticle(Article article, List<Integer> tagIds) {
        try {
            int result = articleMapper.update(article);
            if (result > 0) {
                return Result.success(article);
            }
            return Result.error("更新文章失败");
        } catch (Exception e) {
            logger.error("更新文章异常", e);
            return Result.error("更新文章异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> deleteArticle(Integer articleId, Integer userId) {
        try {
            Article article = articleMapper.selectByArticleId(articleId);
            if (article == null) {
                return Result.error("文章不存在");
            }
            if (!article.getAuthorId().equals(userId)) {
                return Result.error("无权限删除此文章");
            }
            int result = articleMapper.delete(articleId);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("删除文章失败");
        } catch (Exception e) {
            logger.error("删除文章异常", e);
            return Result.error("删除文章异常：" + e.getMessage());
        }
    }

    @Override
    public Result<List<Article>> getHotArticles(Integer limit) {
        try {
            List<Article> articles = articleMapper.selectArticleListByPage(0, limit);
            return Result.success(articles);
        } catch (Exception e) {
            logger.error("获取热门文章异常", e);
            return Result.error("获取热门文章异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Boolean> incrementPageView(Integer articleId) {
        try {
            int result = articleMapper.updatePageView(articleId);
            if (result > 0) {
                return Result.success(true);
            }
            return Result.error("增加浏览量失败");
        } catch (Exception e) {
            logger.error("增加浏览量异常", e);
            return Result.error("增加浏览量异常：" + e.getMessage());
        }
    }
}
