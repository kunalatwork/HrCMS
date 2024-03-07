package com.HrCMS.Wrapper;

import com.HrCMS.Service.CustomUserDetailService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
public class UserWrapper {

    public UserWrapper(Integer id, String firstName, String lastName, String username, String password, String email, String role, String status) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    private Integer Id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private String role;

    private String status;

}
