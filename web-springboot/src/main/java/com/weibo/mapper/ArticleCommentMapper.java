package com.weibo.mapper;

import com.weibo.model.ArticleComment;
import com.weibo.model.ArticleReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 文章评论Mapper接口
 */
@Mapper
public interface ArticleCommentMapper {

    /**
     * 根据文章ID查询评论列表
     */
    List<ArticleComment> selectByArticleId(@Param("articleId") Integer articleId);

    /**
     * 新增评论
     */
    int insert(ArticleComment comment);

    /**
     * 删除评论
     */
    int delete(@Param("commentId") Integer commentId);

    /**
     * 更新评论点赞数
     */
    int updateStarNum(@Param("commentId") Integer commentId, @Param("num") int num);

    /**
     * 查询评论的回复列表
     */
    List<ArticleReply> selectReplyByCommentId(@Param("commentId") Integer commentId);

    /**
     * 新增回复
     */
    int insertReply(ArticleReply reply);

    /**
     * 删除回复
     */
    int deleteReply(@Param("replyId") Integer replyId);

    /**
     * 更新回复点赞数
     */
    int updateReplyStarNum(@Param("replyId") Integer replyId, @Param("num") int num);

    /**
     * 检查用户是否点赞评论
     */
    int checkCommentStar(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    /**
     * 添加评论点赞记录
     */
    int insertCommentStar(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    /**
     * 删除评论点赞记录
     */
    int deleteCommentStar(@Param("userId") Integer userId, @Param("commentId") Integer commentId);
}
