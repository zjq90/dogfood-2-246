package com.weibo.service.impl;

import com.weibo.common.Result;
import com.weibo.dto.PageResult;
import com.weibo.mapper.ArticleMapper;
import com.weibo.model.Article;
import com.weibo.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文章服务实现类
 * 处理文章相关的业务逻辑
 * 
 * @author weibo Team
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取文章详情
     */
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

    /**
     * 分页获取文章列表
     */
    @Override
    public Result<PageResult<Article>> getArticleList(Integer pageNum, Integer pageSize, Integer tagId, String keyword) {
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
            
            PageResult<Article> pageResult = new PageResult<>(articles, total, pageNum, pageSize);
            return Result.success(pageResult);
        } catch (Exception e) {
            logger.error("获取文章列表异常", e);
            return Result.error("获取文章列表异常：" + e.getMessage());
        }
    }

    /**
     * 获取用户的文章列表
     */
    @Override
    public Result<PageResult<Article>> getUserArticles(Integer userId, Integer pageNum, Integer pageSize) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List<Article> articles = articleMapper.selectByAuthorId(userId, offset, pageSize);
            int total = articleMapper.selectCountByAuthorId(userId);
            
            PageResult<Article> pageResult = new PageResult<>(articles, total, pageNum, pageSize);
            return Result.success(pageResult);
        } catch (Exception e) {
            logger.error("获取用户文章列表异常", e);
            return Result.error("获取用户文章列表异常：" + e.getMessage());
        }
    }

    /**
     * 发布文章
     */
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

    /**
     * 更新文章
     */
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

    /**
     * 删除文章
     */
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

    /**
     * 获取热门文章
     */
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

    /**
     * 增加文章浏览量
     */
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
