package com.it.service;

import com.it.common.PageResult;
import com.it.common.Result;
import com.it.model.Article;
import java.util.List;

/**
 * 文章服务接口
 * 
 * @author weibo Team
 */
public interface ArticleService {

    /**
     * 获取文章详情
     * 
     * @param articleId 文章ID
     * @param userId 当前用户ID（可选，用于判断是否点赞/收藏）
     * @return 文章详情
     */
    Result<Article> getArticleDetail(Integer articleId, Integer userId);

    /**
     * 分页获取文章列表
     * 
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param tagId 标签ID（可选）
     * @param keyword 关键词（可选）
     * @return 文章列表
     */
    Result<PageResult<Article>> getArticleList(Integer pageNum, Integer pageSize, Integer tagId, String keyword);

    /**
     * 获取用户的文章列表
     * 
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 文章列表
     */
    Result<PageResult<Article>> getUserArticles(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 发布文章
     * 
     * @param article 文章对象
     * @param tagIds 标签ID列表（可选）
     * @return 发布结果
     */
    Result<Article> publishArticle(Article article, List<Integer> tagIds);

    /**
     * 编辑文章
     * 
     * @param article 文章对象
     * @param tagIds 标签ID列表（可选）
     * @return 编辑结果
     */
    Result<Article> updateArticle(Article article, List<Integer> tagIds);

    /**
     * 删除文章
     * 
     * @param articleId 文章ID
     * @param userId 用户ID（验证权限）
     * @return 删除结果
     */
    Result<Boolean> deleteArticle(Integer articleId, Integer userId);

    /**
     * 获取热门文章
     * 
     * @param limit 返回条数
     * @return 热门文章列表
     */
    Result<List<Article>> getHotArticles(Integer limit);

    /**
     * 增加文章浏览量
     * 
     * @param articleId 文章ID
     * @return 操作结果
     */
    Result<Boolean> incrementPageView(Integer articleId);
}
