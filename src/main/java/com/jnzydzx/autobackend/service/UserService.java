package com.jnzydzx.autobackend.service;

import com.jnzydzx.autobackend.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User addNewUser(User user);
    List<User> acquireUsers();

    /* Repository Curd operations */
    int updateUserByAccountName(String accountName, String accountPwd);
    boolean deleteUserByAccountName(String accountName);
}