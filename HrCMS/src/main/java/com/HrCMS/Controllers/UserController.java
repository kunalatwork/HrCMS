package com.HrCMS.Controllers;

import com.HrCMS.Models.Users;
import com.HrCMS.Wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;


@RequestMapping(value = "/api/users")
public interface UserController {


    @GetMapping("/welcome")
    public String home();

    @PostMapping(value = "/signup")
   public ResponseEntity<String> signUp(@RequestBody(required=true) Map<String, String> requestMap);

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserWrapper>> getAllUsers();

    @PutMapping(value = "/allUsers/{id}")
    ResponseEntity<String> updateUser(Users user, int id);



    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String,String> requestMap);

//    @GetMapping(value="/name")
//    public String getName(Principal principal);

}
