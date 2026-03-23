package com.it.mapper;

import com.it.model.Doumail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


@Mapper
public interface DoumailMapper {

    
    List<Doumail> selectConversationList(@Param("userId") Integer userId,
                                         @Param("offset") Integer offset,
                                         @Param("pageSize") Integer pageSize);

    
    List<Doumail> selectDoumailDetail(@Param("userId") Integer userId,
                                      @Param("targetUserId") Integer targetUserId,
                                      @Param("offset") Integer offset,
                                      @Param("pageSize") Integer pageSize);

    
    int insertDoumail(Doumail doumail);

    
    int markAsRead(@Param("fromUserId") Integer fromUserId,
                   @Param("toUserId") Integer toUserId);

    
    int deleteDoumail(@Param("doumailId") Integer doumailId,
                      @Param("userId") Integer userId,
                      @Param("status") Integer status);

    
    Integer selectUnreadCount(@Param("userId") Integer userId);

    
    Long selectConversationCount(@Param("userId") Integer userId);

    
    Long selectDoumailCount(@Param("userId") Integer userId,
                            @Param("targetUserId") Integer targetUserId);
}
