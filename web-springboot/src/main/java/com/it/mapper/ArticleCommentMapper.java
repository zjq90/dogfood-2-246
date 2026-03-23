package com.it.mapper;

import com.it.model.ArticleComment;
import com.it.model.ArticleReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


@Mapper
public interface ArticleCommentMapper {

    
    List<ArticleComment> selectByArticleId(@Param("articleId") Integer articleId);

    
    int insert(ArticleComment comment);

    
    int delete(@Param("commentId") Integer commentId);

    
    int updateStarNum(@Param("commentId") Integer commentId, @Param("num") int num);

    
    List<ArticleReply> selectReplyByCommentId(@Param("commentId") Integer commentId);

    
    int insertReply(ArticleReply reply);

    
    int deleteReply(@Param("replyId") Integer replyId);

    
    int updateReplyStarNum(@Param("replyId") Integer replyId, @Param("num") int num);

    
    int checkCommentStar(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    
    int insertCommentStar(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    
    int deleteCommentStar(@Param("userId") Integer userId, @Param("commentId") Integer commentId);
}
