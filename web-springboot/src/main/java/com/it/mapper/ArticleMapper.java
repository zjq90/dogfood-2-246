package com.it.mapper;

import com.it.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


@Mapper
public interface ArticleMapper {

    
    Article selectById(@Param("articleId") Integer articleId);

    
    List<Article> selectArticleList(@Param("offset") Integer offset,
                                    @Param("pageSize") Integer pageSize,
                                    @Param("tagId") Integer tagId,
                                    @Param("keyword") String keyword);

    
    Long selectArticleCount(@Param("tagId") Integer tagId, @Param("keyword") String keyword);

    
    List<Article> selectByUserId(@Param("userId") Integer userId,
                                 @Param("offset") Integer offset,
                                 @Param("pageSize") Integer pageSize);

    
    Long selectCountByUserId(@Param("userId") Integer userId);

    
    int insertArticle(Article article);

    
    int updateArticle(Article article);

    
    int deleteById(@Param("articleId") Integer articleId);

    
    int incrementPageView(@Param("articleId") Integer articleId);

    
    int updateStats(@Param("articleId") Integer articleId,
                    @Param("type") Integer type,
                    @Param("increment") Integer increment);

    
    List<Article> selectHotArticles(@Param("limit") Integer limit);

    
    int insertArticleTag(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);

    
    int deleteArticleTags(@Param("articleId") Integer articleId);
}
