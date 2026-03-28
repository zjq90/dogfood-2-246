package com.it.mapper;

import com.it.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 文章Mapper接口
 * 
 * @author weibo Team
 */
@Mapper
public interface ArticleMapper {

    /**
     * 根据文章ID查询文章详情（包含作者信息）
     * 
     * @param articleId 文章ID
     * @return 文章对象
     */
    Article selectById(@Param("articleId") Integer articleId);

    /**
     * 分页查询文章列表（包含作者信息）
     * 
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @param tagId 标签ID（可选）
     * @param keyword 关键词（可选）
     * @return 文章列表
     */
    List<Article> selectArticleList(@Param("offset") Integer offset,
                                    @Param("pageSize") Integer pageSize,
                                    @Param("tagId") Integer tagId,
                                    @Param("keyword") String keyword);

    /**
     * 查询文章总数
     * 
     * @param tagId 标签ID（可选）
     * @param keyword 关键词（可选）
     * @return 文章总数
     */
    Long selectArticleCount(@Param("tagId") Integer tagId, @Param("keyword") String keyword);

    /**
     * 根据用户ID查询用户的文章列表
     * 
     * @param userId 用户ID
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 文章列表
     */
    List<Article> selectByUserId(@Param("userId") Integer userId,
                                 @Param("offset") Integer offset,
                                 @Param("pageSize") Integer pageSize);

    /**
     * 查询用户的文章总数
     * 
     * @param userId 用户ID
     * @return 文章总数
     */
    Long selectCountByUserId(@Param("userId") Integer userId);

    /**
     * 新增文章
     * 
     * @param article 文章对象
     * @return 影响行数
     */
    int insertArticle(Article article);

    /**
     * 更新文章
     * 
     * @param article 文章对象
     * @return 影响行数
     */
    int updateArticle(Article article);

    /**
     * 删除文章
     * 
     * @param articleId 文章ID
     * @return 影响行数
     */
    int deleteById(@Param("articleId") Integer articleId);

    /**
     * 增加文章浏览量
     * 
     * @param articleId 文章ID
     * @return 影响行数
     */
    int incrementPageView(@Param("articleId") Integer articleId);

    /**
     * 更新文章统计数据（点赞、收藏、评论、转发数）
     * 
     * @param articleId 文章ID
     * @param type 类型：1-点赞；2-收藏；3-评论；4-转发
     * @param increment 增量（1或-1）
     * @return 影响行数
     */
    int updateStats(@Param("articleId") Integer articleId,
                    @Param("type") Integer type,
                    @Param("increment") Integer increment);

    /**
     * 获取热门文章（按浏览量排序）
     * 
     * @param limit 返回条数
     * @return 热门文章列表
     */
    List<Article> selectHotArticles(@Param("limit") Integer limit);

    /**
     * 文章-标签关联
     * 
     * @param articleId 文章ID
     * @param tagId 标签ID
     * @return 影响行数
     */
    int insertArticleTag(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);

    /**
     * 删除文章的标签关联
     * 
     * @param articleId 文章ID
     * @return 影响行数
     */
    int deleteArticleTags(@Param("articleId") Integer articleId);
    
    /**
     * 更新文章点赞数
     * 
     * @param articleId 文章ID
     * @param increment 增量（1或-1）
     * @return 影响行数
     */
    int updateStarNum(@Param("articleId") Integer articleId, @Param("increment") int increment);
    
    /**
     * 更新文章收藏数
     * 
     * @param articleId 文章ID
     * @param increment 增量（1或-1）
     * @return 影响行数
     */
    int updateCollectionNum(@Param("articleId") Integer articleId, @Param("increment") int increment);
    
    /**
     * 更新文章评论数
     * 
     * @param articleId 文章ID
     * @param increment 增量（1或-1）
     * @return 影响行数
     */
    int updateCommentNum(@Param("articleId") Integer articleId, @Param("increment") int increment);
    
    /**
     * 更新文章转发数
     * 
     * @param articleId 文章ID
     * @param increment 增量（1或-1）
     * @return 影响行数
     */
    int updateShareNum(@Param("articleId") Integer articleId, @Param("increment") int increment);
}
