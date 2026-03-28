package com.it.mapper;

import com.it.model.ArticleCollection;
import com.it.model.ArticleComment;
import com.it.model.ArticleReply;
import com.it.model.ArticleStar;
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
     * 查询点赞记录
     * 
     * @param userId 用户ID
     * @param typeId 对象ID
     * @param type 对象类型：1-文章；2-评论；3-回复
     * @return 点赞对象
     */
    ArticleStar selectStar(@Param("userId") Integer userId,
                           @Param("typeId") Integer typeId,
                           @Param("type") Integer type);

    /**
     * 新增点赞
     * 
     * @param star 点赞对象
     * @return 影响行数
     */
    int insertStar(ArticleStar star);

    /**
     * 取消点赞
     * 
     * @param starId 点赞ID
     * @return 影响行数
     */
    int deleteStar(@Param("starId") Integer starId);

    // ==================== 收藏相关 ====================
    
    /**
     * 查询用户收藏的文章列表
     * 
     * @param userId 用户ID
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 收藏列表
     */
    List<ArticleCollection> selectCollectionList(@Param("userId") Integer userId,
                                                 @Param("offset") Integer offset,
                                                 @Param("pageSize") Integer pageSize);

    /**
     * 查询用户收藏数
     * 
     * @param userId 用户ID
     * @return 收藏数
     */
    Long selectCollectionCount(@Param("userId") Integer userId);

    /**
     * 查询收藏记录
     * 
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 收藏对象
     */
    ArticleCollection selectCollection(@Param("userId") Integer userId,
                                       @Param("articleId") Integer articleId);

    /**
     * 新增收藏
     * 
     * @param collection 收藏对象
     * @return 影响行数
     */
    int insertCollection(ArticleCollection collection);

    /**
     * 取消收藏
     * 
     * @param collectionId 收藏ID
     * @return 影响行数
     */
    int deleteCollection(@Param("collectionId") Integer collectionId);
    
    // ==================== 简化方法 ====================
    
    /**
     * 检查文章点赞记录
     */
    int checkArticleStar(@Param("userId") Integer userId, @Param("articleId") Integer articleId);
    
    /**
     * 新增文章点赞（简化方法）
     */
    int insertArticleStar(@Param("userId") Integer userId, @Param("articleId") Integer articleId);
    
    /**
     * 删除文章点赞（简化方法）
     */
    int deleteArticleStar(@Param("userId") Integer userId, @Param("articleId") Integer articleId);
    
    /**
     * 检查文章收藏记录
     */
    int checkArticleCollection(@Param("userId") Integer userId, @Param("articleId") Integer articleId);
    
    /**
     * 新增文章收藏（简化方法）
     */
    int insertArticleCollection(@Param("userId") Integer userId, @Param("articleId") Integer articleId);
    
    /**
     * 删除文章收藏（简化方法）
     */
    int deleteArticleCollection(@Param("userId") Integer userId, @Param("articleId") Integer articleId);
    
    /**
     * 检查文章转发记录
     */
    int checkArticleShare(@Param("userId") Integer userId, @Param("articleId") Integer articleId);
    
    /**
     * 新增文章转发（简化方法）
     */
    int insertArticleShare(@Param("userId") Integer userId, @Param("articleId") Integer articleId);
}
