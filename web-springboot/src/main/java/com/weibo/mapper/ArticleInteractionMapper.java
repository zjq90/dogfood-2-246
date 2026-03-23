package com.weibo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleInteractionMapper {

    int checkArticleStar(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    int insertArticleStar(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    int deleteArticleStar(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    int checkArticleCollection(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    int insertArticleCollection(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    int deleteArticleCollection(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    int checkArticleShare(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    int insertArticleShare(@Param("userId") Integer userId, @Param("articleId") Integer articleId);
}
