package com.it.mapper;

import com.it.model.ArticleCollection;
import com.it.model.ArticleComment;
import com.it.model.ArticleReply;
import com.it.model.ArticleStar;
import com.it.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ArticleInteractionMapper {

    List<ArticleComment> selectCommentList(@Param("articleId") Integer articleId,
                                           @Param("offset") Integer offset,
                                           @Param("pageSize") Integer pageSize);

    Long selectCommentCount(@Param("articleId") Integer articleId);

    int insertComment(ArticleComment comment);

    int deleteComment(@Param("commentId") Integer commentId, @Param("userId") Integer userId);

    List<ArticleReply> selectReplyList(@Param("commentId") Integer commentId);

    int insertReply(ArticleReply reply);

    int deleteReply(@Param("replyId") Integer replyId, @Param("userId") Integer userId);

    ArticleStar selectStar(@Param("userId") Integer userId,
                           @Param("typeId") Integer typeId,
                           @Param("type") Integer type);

    int insertStar(ArticleStar star);

    int deleteStar(@Param("starId") Integer starId);

    List<ArticleCollection> selectCollectionList(@Param("userId") Integer userId,
                                                 @Param("offset") Integer offset,
                                                 @Param("pageSize") Integer pageSize);

    Long selectCollectionCount(@Param("userId") Integer userId);

    ArticleCollection selectCollection(@Param("userId") Integer userId,
                                       @Param("articleId") Integer articleId);

    int insertCollection(ArticleCollection collection);

    int deleteCollection(@Param("collectionId") Integer collectionId);

    int checkStarExists(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    int checkCollectExists(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    int incrementStarCount(@Param("articleId") Integer articleId);

    int decrementStarCount(@Param("articleId") Integer articleId);

    int incrementCollectCount(@Param("articleId") Integer articleId);

    int decrementCollectCount(@Param("articleId") Integer articleId);

    int incrementShareCount(@Param("articleId") Integer articleId);

    int incrementCommentCount(@Param("articleId") Integer articleId);

    int decrementCommentCount(@Param("articleId") Integer articleId);

    List<ArticleComment> selectCommentsByArticleId(@Param("articleId") Integer articleId);

    List<ArticleReply> selectRepliesByCommentId(@Param("commentId") Integer commentId);

    List<User> selectStarUsers(@Param("articleId") Integer articleId);

    List<User> selectCollectUsers(@Param("articleId") Integer articleId);
}
