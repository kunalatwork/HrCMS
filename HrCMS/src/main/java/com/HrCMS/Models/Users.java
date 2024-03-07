package com.HrCMS.Models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;
import java.util.List;

@NamedQuery(name = "Users.findByEmailId", query = "select u from Users u where u.email=:email")
@NamedQuery(name = "Users.findByUsername", query = "select u from Users u where u.username=:username")
@NamedQuery(name = "Users.getAllUsers" , query = "select new com.HrCMS.Wrapper.UserWrapper(u.id, u.firstName,u.lastName, u.username,u.password, u.email, u.role, u.status) from Users u where u.role='user'")
@NamedQuery(name = "Users.getAllUsersData" , query = "select new com.HrCMS.Wrapper.UserWrapper(u.id, u.firstName,u.lastName, u.username,u.password, u.email, u.role, u.status) from Users u where u.role='user' and u.role='admin'")

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name="Employee")

public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer Id;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="role")
    private String role;

    @Column(name="status")
    private String status;

}
