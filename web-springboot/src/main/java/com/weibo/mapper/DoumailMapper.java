package com.weibo.mapper;

import com.weibo.model.Doumail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DoumailMapper {

    List<Doumail> selectConversationList(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    int countConversationList(@Param("userId") Integer userId);

    List<Doumail> selectByUserIds(@Param("userId") Integer userId, @Param("targetUserId") Integer targetUserId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    int countByUserIds(@Param("userId") Integer userId, @Param("targetUserId") Integer targetUserId);

    int insert(Doumail doumail);

    int updateReadStatus(@Param("fromUserId") Integer fromUserId, @Param("toUserId") Integer toUserId);

    int delete(@Param("doumailId") Integer doumailId, @Param("userId") Integer userId);

    int countUnread(@Param("userId") Integer userId);
}
