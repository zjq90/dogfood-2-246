package com.it.service;

import com.it.common.PageResult;
import com.it.common.Result;
import com.it.model.Article;
import java.util.List;


public interface ArticleService {

    
    Result<Article> getArticleDetail(Integer articleId, Integer userId);

    
    Result<PageResult<Article>> getArticleList(Integer pageNum, Integer pageSize, Integer tagId, String keyword);

    
    Result<PageResult<Article>> getUserArticles(Integer userId, Integer pageNum, Integer pageSize);

    
    Result<Article> publishArticle(Article article, List<Integer> tagIds);

    
    Result<Article> updateArticle(Article article, List<Integer> tagIds);

    
    Result<Boolean> deleteArticle(Integer articleId, Integer userId);

    
    Result<List<Article>> getHotArticles(Integer limit);

    
    Result<Boolean> incrementPageView(Integer articleId);
}
