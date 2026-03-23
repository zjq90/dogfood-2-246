package com.weibo.mapper;

import com.weibo.model.ArticleComment;
import com.weibo.model.ArticleReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 文章互动Mapper接口（评论、回复、点赞、收藏）
 * 
 * @author weibo Team
 */
@Mapper
public interface ArticleInteractionMapper {

    // ==================== 评论相关 ====================
    
    /**
     * 查询文章评论列表
     * 
     * @param articleId 文章ID
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 评论列表
     */
    List<ArticleComment> selectCommentList(@Param("articleId") Integer articleId,
                                           @Param("offset") Integer offset,
                                           @Param("pageSize") Integer pageSize);

    /**
     * 查询文章评论数
     * 
     * @param articleId 文章ID
     * @return 评论数
     */
    Long selectCommentCount(@Param("articleId") Integer articleId);

    /**
     * 新增评论
     * 
     * @param comment 评论对象
     * @return 影响行数
     */
    int insertComment(ArticleComment comment);

    /**
     * 删除评论
     * 
     * @param commentId 评论ID
     * @param userId 用户ID（验证权限）
     * @return 影响行数
     */
    int deleteComment(@Param("commentId") Integer commentId, @Param("userId") Integer userId);

    // ==================== 回复相关 ====================
    
    /**
     * 查询评论的回复列表
     * 
     * @param commentId 评论ID
     * @return 回复列表
     */
    List<ArticleReply> selectReplyList(@Param("commentId") Integer commentId);

    /**
     * 新增回复
     * 
     * @param reply 回复对象
     * @return 影响行数
     */
    int insertReply(ArticleReply reply);

    /**
     * 删除回复
     * 
     * @param replyId 回复ID
     * @param userId 用户ID（验证权限）
     * @return 影响行数
     */
    int deleteReply(@Param("replyId") Integer replyId, @Param("userId") Integer userId);

    // ==================== 点赞相关 ====================
    
    /**
     * 检查用户是否点赞文章
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 数量
     */
    int checkArticleStar(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    /**
     * 插入文章点赞记录
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 影响行数
     */
    int insertArticleStar(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    /**
     * 删除文章点赞记录
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 影响行数
     */
    int deleteArticleStar(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    /**
     * 检查用户是否转发文章
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 数量
     */
    int checkArticleShare(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    /**
     * 插入文章转发记录
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 影响行数
     */
    int insertArticleShare(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    // ==================== 收藏相关 ====================
    
    /**
     * 检查用户是否收藏文章
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 数量
     */
    int checkArticleCollection(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    /**
     * 插入文章收藏记录
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 影响行数
     */
    int insertArticleCollection(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    /**
     * 删除文章收藏记录
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 影响行数
     */
    int deleteArticleCollection(@Param("userId") Integer userId, @Param("articleId") Integer articleId);
}
