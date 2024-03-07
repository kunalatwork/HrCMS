package com.HrCMS.Service;

import com.HrCMS.Models.Users;
import com.HrCMS.Wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface UserService {
    public ResponseEntity<String> signUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);

    public ResponseEntity<List<UserWrapper>> getAllUsers();
}
