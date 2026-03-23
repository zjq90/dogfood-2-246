package com.weibo.service.impl;

import com.weibo.dto.PageResult;
import com.weibo.dto.Result;
import com.weibo.mapper.ArticleMapper;
import com.weibo.model.Article;
import com.weibo.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 文章服务实现类
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result<Article> getArticleDetail(Integer articleId, Integer userId) {
        log.info("获取文章详情: {}", articleId);
        
        if (articleId == null) {
            return Result.error("文章ID不能为空");
        }

        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            return Result.error("文章不存在");
        }

        // 增加浏览量
        articleMapper.updatePageView(articleId);

        return Result.success(article);
    }

    @Override
    public Result<PageResult<Article>> getArticleList(Integer pageNum, Integer pageSize, Integer tagId, String keyword) {
        log.info("获取文章列表: pageNum={}, pageSize={}, tagId={}, keyword={}", pageNum, pageSize, tagId, keyword);
        
        int offset = (pageNum - 1) * pageSize;
        List<Article> articles = articleMapper.selectArticleList(offset, pageSize, tagId, keyword);
        Long total = articleMapper.selectArticleCount(tagId, keyword);
        
        PageResult<Article> pageResult = PageResult.build(articles, total, pageNum, pageSize);
        return Result.success(pageResult);
    }

    @Override
    public Result<PageResult<Article>> getUserArticles(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("获取用户文章列表: userId={}", userId);
        
        int offset = (pageNum - 1) * pageSize;
        List<Article> articles = articleMapper.selectByUserId(userId, offset, pageSize);
        Long total = articleMapper.selectCountByUserId(userId);
        
        PageResult<Article> pageResult = PageResult.build(articles, total, pageNum, pageSize);
        return Result.success(pageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Article> publishArticle(Article article, List<Integer> tagIds) {
        log.info("发布文章: {}", article.getTitle());
        
        if (article.getTitle() == null || article.getTitle().isEmpty()) {
            return Result.error("文章标题不能为空");
        }
        
        if (article.getContent() == null || article.getContent().isEmpty()) {
            return Result.error("文章内容不能为空");
        }

        article.setPublishedTime(new Date());
        article.setStarNum(0);
        article.setCollectionNum(0);
        article.setCommentNum(0);
        article.setShareNum(0);
        article.setPageView(0);
        article.setStick(0);

        int result = articleMapper.insert(article);
        if (result > 0) {
            // 处理标签
            if (tagIds != null && !tagIds.isEmpty()) {
                for (Integer tagId : tagIds) {
                    articleMapper.insertArticleTag(article.getArticleId(), tagId);
                }
            }
            return Result.success("发布成功", article);
        } else {
            return Result.error("发布失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Article> updateArticle(Article article, List<Integer> tagIds) {
        log.info("更新文章: {}", article.getArticleId());
        
        if (article.getArticleId() == null) {
            return Result.error("文章ID不能为空");
        }

        Article existingArticle = articleMapper.selectById(article.getArticleId());
        if (existingArticle == null) {
            return Result.error("文章不存在");
        }

        int result = articleMapper.update(article);
        if (result > 0) {
            // 更新标签
            if (tagIds != null) {
                articleMapper.deleteArticleTags(article.getArticleId());
                for (Integer tagId : tagIds) {
                    articleMapper.insertArticleTag(article.getArticleId(), tagId);
                }
            }
            return Result.success("更新成功", article);
        } else {
            return Result.error("更新失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteArticle(Integer articleId, Integer userId) {
        log.info("删除文章: {}", articleId);
        
        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            return Result.error("文章不存在");
        }

        // 验证权限（只能删除自己的文章，管理员可以删除所有）
        // 这里简化处理，实际应该查询用户权限
        if (!article.getAuthorId().equals(userId)) {
            return Result.error("无权删除该文章");
        }

        int result = articleMapper.delete(articleId);
        if (result > 0) {
            return Result.success("删除成功", true);
        } else {
            return Result.error("删除失败");
        }
    }

    @Override
    public Result<List<Article>> getHotArticles(Integer limit) {
        log.info("获取热门文章: limit={}", limit);
        
        List<Article> articles = articleMapper.selectHotArticles(limit);
        return Result.success(articles);
    }

    @Override
    public Result<Boolean> incrementPageView(Integer articleId) {
        log.info("增加文章浏览量: {}", articleId);
        
        int result = articleMapper.updatePageView(articleId);
        if (result > 0) {
            return Result.success("操作成功", true);
        } else {
            return Result.error("操作失败");
        }
    }
}
