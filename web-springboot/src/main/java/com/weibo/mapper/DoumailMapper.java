package com.weibo.mapper;

import com.weibo.model.Doumail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 豆邮Mapper接口
 * 提供豆邮数据的增删改查操作
 * 
 * @author weibo Team
 */
@Mapper
public interface DoumailMapper {

    /**
     * 查询用户的豆邮会话列表（去重）
     * 
     * @param userId 用户ID
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 豆邮列表
     */
    List<Doumail> selectConversationList(@Param("userId") Integer userId,
                                         @Param("offset") Integer offset,
                                         @Param("pageSize") Integer pageSize);

    /**
     * 查询两个用户之间的豆邮详情
     * 
     * @param userId 当前用户ID
     * @param targetUserId 对方用户ID
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @return 豆邮列表
     */
    List<Doumail> selectDoumailDetail(@Param("userId") Integer userId,
                                      @Param("targetUserId") Integer targetUserId,
                                      @Param("offset") Integer offset,
                                      @Param("pageSize") Integer pageSize);

    /**
     * 发送豆邮
     * 
     * @param doumail 豆邮对象
     * @return 影响行数
     */
    int insertDoumail(Doumail doumail);

    /**
     * 标记已读
     * 
     * @param fromUserId 发送者ID
     * @param toUserId 接收者ID
     * @return 影响行数
     */
    int markAsRead(@Param("fromUserId") Integer fromUserId,
                   @Param("toUserId") Integer toUserId);

    /**
     * 删除豆邮（逻辑删除，更新状态）
     * 
     * @param doumailId 豆邮ID
     * @param userId 操作人ID
     * @param status 新状态：1-发送者删除；2-接收者删除
     * @return 影响行数
     */
    int deleteDoumail(@Param("doumailId") Integer doumailId,
                      @Param("userId") Integer userId,
                      @Param("status") Integer status);

    /**
     * 查询未读豆邮数
     * 
     * @param userId 用户ID
     * @return 未读数量
     */
    Integer selectUnreadCount(@Param("userId") Integer userId);

    /**
     * 查询会话总数
     * 
     * @param userId 用户ID
     * @return 会话总数
     */
    Long selectConversationCount(@Param("userId") Integer userId);

    /**
     * 查询两个用户之间的豆邮总数
     * 
     * @param userId 当前用户ID
     * @param targetUserId 对方用户ID
     * @return 豆邮总数
     */
    Long selectDoumailCount(@Param("userId") Integer userId,
                            @Param("targetUserId") Integer targetUserId);
}
