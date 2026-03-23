package com.weibo.mapper;

import com.weibo.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {

    Article selectByArticleId(@Param("articleId") Integer articleId);

    int insert(Article article);

    int update(Article article);

    int delete(@Param("articleId") Integer articleId);

    List<Article> selectArticleListByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int selectArticleCount();

    List<Article> selectByAuthorId(@Param("authorId") Integer authorId, @Param("offset") int offset, @Param("pageSize") int pageSize);

    int selectCountByAuthorId(@Param("authorId") Integer authorId);

    List<Article> selectSearchByPage(@Param("searchContent") String searchContent, @Param("offset") int offset, @Param("pageSize") int pageSize);

    int selectSearchCount(@Param("searchContent") String searchContent);

    int updateStarNum(@Param("articleId") Integer articleId, @Param("num") int num);

    int updateCollectionNum(@Param("articleId") Integer articleId, @Param("num") int num);

    int updateCommentNum(@Param("articleId") Integer articleId, @Param("num") int num);

    int updateShareNum(@Param("articleId") Integer articleId, @Param("num") int num);

    int updatePageView(@Param("articleId") Integer articleId);

    List<Article> selectCollectionByPage(@Param("userId") Integer userId, @Param("offset") int offset, @Param("pageSize") int pageSize);

    int selectCollectionCount(@Param("userId") Integer userId);
}
