package com.it.service;

import com.it.common.Result;
import com.it.model.User;
import java.util.List;


public interface UserService {

    
    Result<User> login(String username, String password);

    
    Result<User> register(String username, String password);

    
    boolean isUsernameExists(String username);

    
    User getUserByUsername(String username);

    
    User getUserByUserId(Integer userId);

    
    Result<Boolean> updatePersonage(User user);

    
    Result<Boolean> updatePortrait(Integer userId, String portrait);

    
    Result<Integer> generateResetCode(String username);

    
    Result<User> verifyResetCode(Integer code);

    
    Result<Boolean> resetPassword(Integer code, String newPassword);

    
    Result<Boolean> changePassword(Integer userId, String oldPassword, String newPassword);

    
    Result<List<User>> searchUsers(String keyword);

    
    Result<List<User>> getAllUsers();

    
    Result<Boolean> updateStatus(Integer userId, Integer status);

    
    Result<Boolean> reportUser(Integer userId);
}
