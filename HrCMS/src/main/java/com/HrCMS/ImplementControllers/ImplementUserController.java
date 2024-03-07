package com.HrCMS.ImplementControllers;

import com.HrCMS.Controllers.UserController;
import com.HrCMS.Models.Users;
import com.HrCMS.Service.UserService;
import com.HrCMS.Utils.javaUtils;
import com.HrCMS.Wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ImplementUserController implements UserController {
    @Autowired
    UserService userService;


    @Override
    public String home() {
        return "Welcome";
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

        try {
            return userService.signUp(requestMap);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.out.println("Issue is : " + e);
        }
        return javaUtils.getResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try{
            return userService.getAllUsers();
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Users user, int id) {
        return null;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.out.println("Error is : "+ e);
        }
        return javaUtils.getResponseEntity("Invalid Username and Password",  HttpStatus.UNAUTHORIZED);
    }


//    @Override
//    public String getName(Principal principal) {
//        return principal.getName();
//    }


}
