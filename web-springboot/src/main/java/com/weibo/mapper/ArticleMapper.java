package com.weibo.mapper;

import com.weibo.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 文章Mapper接口
 * 提供文章数据的增删改查操作
 * 
 * @author weibo Team
 */
@Mapper
public interface ArticleMapper {

    /**
     * 根据文章ID查询文章详情
     * 
     * @param articleId 文章ID
     * @return 文章对象
     */
    Article selectByArticleId(@Param("articleId") Integer articleId);

    /**
     * 分页查询文章列表
     * 
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 文章列表
     */
    List<Article> selectArticleListByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询文章总数
     * 
     * @return 文章总数
     */
    int selectArticleCount();

    /**
     * 根据作者ID查询文章列表
     * 
     * @param authorId 作者ID
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 文章列表
     */
    List<Article> selectByAuthorId(@Param("authorId") Integer authorId,
                                   @Param("offset") Integer offset,
                                   @Param("pageSize") Integer pageSize);

    /**
     * 查询用户的文章总数
     * 
     * @param authorId 作者ID
     * @return 文章总数
     */
    int selectCountByAuthorId(@Param("authorId") Integer authorId);

    /**
     * 搜索文章（分页）
     * 
     * @param searchContent 搜索内容
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 文章列表
     */
    List<Article> selectSearchByPage(@Param("searchContent") String searchContent,
                                     @Param("offset") Integer offset,
                                     @Param("pageSize") Integer pageSize);

    /**
     * 搜索文章总数
     * 
     * @param searchContent 搜索内容
     * @return 文章总数
     */
    int selectSearchCount(@Param("searchContent") String searchContent);

    /**
     * 新增文章
     * 
     * @param article 文章对象
     * @return 影响行数
     */
    int insert(Article article);

    /**
     * 更新文章
     * 
     * @param article 文章对象
     * @return 影响行数
     */
    int update(Article article);

    /**
     * 删除文章
     * 
     * @param articleId 文章ID
     * @return 影响行数
     */
    int delete(@Param("articleId") Integer articleId);

    /**
     * 增加文章浏览量
     * 
     * @param articleId 文章ID
     * @return 影响行数
     */
    int updatePageView(@Param("articleId") Integer articleId);

    /**
     * 更新文章点赞数
     * 
     * @param articleId 文章ID
     * @param num 增量（1或-1）
     * @return 影响行数
     */
    int updateStarNum(@Param("articleId") Integer articleId, @Param("num") Integer num);

    /**
     * 更新文章收藏数
     * 
     * @param articleId 文章ID
     * @param num 增量（1或-1）
     * @return 影响行数
     */
    int updateCollectionNum(@Param("articleId") Integer articleId, @Param("num") Integer num);

    /**
     * 更新文章评论数
     * 
     * @param articleId 文章ID
     * @param num 增量（1或-1）
     * @return 影响行数
     */
    int updateCommentNum(@Param("articleId") Integer articleId, @Param("num") Integer num);

    /**
     * 更新文章转发数
     * 
     * @param articleId 文章ID
     * @param num 增量（1或-1）
     * @return 影响行数
     */
    int updateShareNum(@Param("articleId") Integer articleId, @Param("num") Integer num);
}
