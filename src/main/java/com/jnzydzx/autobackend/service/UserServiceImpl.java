package com.jnzydzx.autobackend.service;


import com.jnzydzx.autobackend.entity.User;
import com.jnzydzx.autobackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Attach a new user
     * @param user The user entity
     * @return The user entity
     */
    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Delete a user by its account name
     * @param accountName One user's account
     * @return If a user had been deleted, it would return a TRUE value
     */
    @Transactional
    @Override
    public boolean deleteUserByAccountName(String accountName) {
        userRepository.deleteUserByAccountName(accountName);
        return !userRepository.existsByAccountName(accountName);
    }

    /**
     * Acquire all users
     * @return Users' list
     */
    @Override
    public List<User> acquireUsers() {
        return userRepository.findAll();
    }

    /**
     * This function focus on update details of a user
     * @param accountName The user's name
     * @param accountPwd The user's password
     * @return The completed status of the operation
     */
    @Transactional
    @Override
    public int updateUserByAccountName(String accountName, String accountPwd) {
        return userRepository.updateUserByAccountName(accountName, accountPwd);
    }
}
