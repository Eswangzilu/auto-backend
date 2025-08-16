package com.jnzydzx.autobackend.repository;

import com.jnzydzx.autobackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * UserService.interface file contains a copy of this interface !!
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /* CURD Operations below */
    @Modifying
    @Query("UPDATE User u SET u.accountName = :userName, u.accountPwd = :userPwd where u.accountName = :userName")
    int updateUserByAccountName(@Param("userName")String accountName, @Param("userPwd")String accountPwd);

    @Modifying
    @Query("DELETE FROM User u WHERE u.accountName = :userName")
    void deleteUserByAccountName(@Param("userName")String accountName);

    /* Default implementation */
    boolean existsByAccountName(String accountName);

}