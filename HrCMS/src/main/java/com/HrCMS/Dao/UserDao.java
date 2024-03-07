package com.HrCMS.Dao;

import com.HrCMS.Models.Users;
import com.HrCMS.Wrapper.UserWrapper;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao extends JpaRepository<Users, Integer> {
    Users findByEmailId(@Param("email") String email);
    Users findByUsername(@Param("username") String username);

    List<UserWrapper> getAllUsers();
    List<UserWrapper>getAllUsersData();
}
