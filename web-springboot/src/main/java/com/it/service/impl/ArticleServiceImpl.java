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

/**
 * 文章服务实现类
 * 
 * @author weibo Team
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 获取文章详情
     * 
     * @param articleId 文章ID
     * @param userId 当前用户ID（可选，用于判断是否点赞/收藏）
     * @return 文章详情
     */
    @Override
    public Result<Article> getArticleDetail(Integer articleId, Integer userId) {
        log.info("get article detail, articleId: {}, userId: {}", articleId, userId);
        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            return Result.error("文章不存在");
        }
        return Result.success("获取成功", article);
    }

    /**
     * 分页获取文章列表
     * 
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param tagId 标签ID（可选）
     * @param keyword 关键词（可选）
     * @return 文章列表
     */
    @Override
    public Result<PageResult<Article>> getArticleList(Integer pageNum, Integer pageSize, Integer tagId, String keyword) {
        log.info("get article list, pageNum: {}, pageSize: {}, tagId: {}, keyword: {}", pageNum, pageSize, tagId, keyword);
        int offset = (pageNum - 1) * pageSize;
        List<Article> list = articleMapper.selectArticleList(offset, pageSize, tagId, keyword);
        Long total = articleMapper.selectArticleCount(tagId, keyword);
        PageResult<Article> pageResult = PageResult.success(pageNum, pageSize, total, list);
        return Result.success("获取成功", pageResult);
    }

    /**
     * 获取用户的文章列表
     * 
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 文章列表
     */
    @Override
    public Result<PageResult<Article>> getUserArticles(Integer userId, Integer pageNum, Integer pageSize) {
        log.info("get user articles, userId: {}, pageNum: {}, pageSize: {}", userId, pageNum, pageSize);
        int offset = (pageNum - 1) * pageSize;
        List<Article> list = articleMapper.selectByUserId(userId, offset, pageSize);
        Long total = articleMapper.selectCountByUserId(userId);
        PageResult<Article> pageResult = PageResult.success(pageNum, pageSize, total, list);
        return Result.success("获取成功", pageResult);
    }

    /**
     * 发布文章
     * 
     * @param article 文章对象
     * @param tagIds 标签ID列表（可选）
     * @return 发布结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Article> publishArticle(Article article, List<Integer> tagIds) {
        log.info("publish article, userId: {}, title: {}", article.getUserId(), article.getTitle());
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        article.setPageView(0);
        article.setCollectCount(0);
        article.setStarCount(0);
        article.setCommentCount(0);
        article.setShareCount(0);
        int result = articleMapper.insertArticle(article);
        if (result > 0) {
            // 处理标签关联
            if (tagIds != null && !tagIds.isEmpty()) {
                for (Integer tagId : tagIds) {
                    articleMapper.insertArticleTag(article.getArticleId(), tagId);
                }
            }
            return Result.success("发布成功", article);
        }
        return Result.error("发布失败");
    }

    /**
     * 编辑文章
     * 
     * @param article 文章对象
     * @param tagIds 标签ID列表（可选）
     * @return 编辑结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Article> updateArticle(Article article, List<Integer> tagIds) {
        log.info("update article, articleId: {}", article.getArticleId());
        article.setUpdateTime(new Date());
        int result = articleMapper.updateArticle(article);
        if (result > 0) {
            // 删除旧的标签关联，添加新的标签关联
            articleMapper.deleteArticleTags(article.getArticleId());
            if (tagIds != null && !tagIds.isEmpty()) {
                for (Integer tagId : tagIds) {
                    articleMapper.insertArticleTag(article.getArticleId(), tagId);
                }
            }
            return Result.success("更新成功", article);
        }
        return Result.error("更新失败");
    }

    /**
     * 删除文章
     * 
     * @param articleId 文章ID
     * @param userId 用户ID（验证权限）
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteArticle(Integer articleId, Integer userId) {
        log.info("delete article, articleId: {}, userId: {}", articleId, userId);
        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            return Result.error("文章不存在");
        }
        if (!article.getUserId().equals(userId)) {
            return Result.error("无权限删除该文章");
        }
        // 删除标签关联
        articleMapper.deleteArticleTags(articleId);
        int result = articleMapper.deleteById(articleId);
        return result > 0 ? Result.success("删除成功", true) : Result.error("删除失败");
    }

    /**
     * 获取热门文章
     * 
     * @param limit 返回条数
     * @return 热门文章列表
     */
    @Override
    public Result<List<Article>> getHotArticles(Integer limit) {
        log.info("get hot articles, limit: {}", limit);
        List<Article> list = articleMapper.selectHotArticles(limit);
        return Result.success("获取成功", list);
    }

    /**
     * 增加文章浏览量
     * 
     * @param articleId 文章ID
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> incrementPageView(Integer articleId) {
        log.info("increment page view, articleId: {}", articleId);
        int result = articleMapper.incrementPageView(articleId);
        return result > 0 ? Result.success("操作成功", true) : Result.error("操作失败");
    }
}
