package com.weibo.service;

import com.weibo.common.Result;
import com.weibo.model.Article;
import java.util.List;

public interface ArticleService {

    Result<Article> getArticleDetail(Integer articleId, Integer userId);

    Result<com.weibo.model.ArticleList> getArticleList(Integer pageNum, Integer pageSize, Integer tagId, String keyword);

    Result<com.weibo.model.ArticleList> getUserArticles(Integer userId, Integer pageNum, Integer pageSize);

    Result<Article> publishArticle(Article article, List<Integer> tagIds);

    Result<Article> updateArticle(Article article, List<Integer> tagIds);

    Result<Boolean> deleteArticle(Integer articleId, Integer userId);

    Result<List<Article>> getHotArticles(Integer limit);

    Result<Boolean> incrementPageView(Integer articleId);
}
