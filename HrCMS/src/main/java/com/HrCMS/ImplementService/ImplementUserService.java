package com.HrCMS.ImplementService;

import com.HrCMS.Dao.UserDao;
import com.HrCMS.JwtServices.JwtFilter;
import com.HrCMS.JwtServices.JwtHelper;
import com.HrCMS.Models.Users;
import com.HrCMS.Service.CustomUserDetailService;
import com.HrCMS.Service.UserService;
import com.HrCMS.Utils.javaUtils;
import com.HrCMS.Wrapper.UserWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImplementUserService implements UserService {
    @Autowired
    private final UserDao userDao;

    @Autowired
    CustomUserDetailService customUserDetailService;
    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    PasswordEncoder passwordEncoder;


    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside SignUp{}", requestMap);
        try {
            if (isContains(requestMap)) {
                Users username = userDao.findByUsername(requestMap.get("username"));
                Users email = userDao.findByEmailId(requestMap.get("email"));
                String password = requestMap.get("password");
                if (Objects.isNull(email)) {
                    if (Objects.isNull(username)) {
                        userDao.save(getUserData(requestMap));
                        return javaUtils.getResponseEntity("Data inserted", HttpStatus.OK);
                    }
                    return javaUtils.getResponseEntity("Username is already exists. Please use other username", HttpStatus.BAD_REQUEST);
                }
                return javaUtils.getResponseEntity("Email is already exists", HttpStatus.BAD_REQUEST);
            }
            return javaUtils.getResponseEntity("Invalid Data", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return javaUtils.getResponseEntity("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean isContains(Map<String, String> requestMap) {
        return requestMap.containsKey("firstName") &&
                requestMap.containsKey("username") &&
                requestMap.containsKey("password") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("role") &&
                requestMap.containsKey("status");
    }

    private Users getUserData(Map<String, String> requestMap) {
        Users user = new Users();
        user.setFirstName(requestMap.get("firstName"));
        user.setLastName(requestMap.get("lastName"));
        user.setUsername(requestMap.get("username"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));
        user.setEmail(requestMap.get("email"));
        user.setRole(requestMap.get("role"));
        user.setStatus(requestMap.get("status"));
        return user;
    }


    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {

        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUsers(), HttpStatus.OK);
            } else if (jwtFilter.isUser()) {
                return new ResponseEntity<>(userDao.getAllUsersData(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        String email = requestMap.get("email");
        String password = requestMap.get("password");
        try {
            Authentication auth = authentication(email, password);
            if (auth.isAuthenticated()) {
                if (customUserDetailService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    String token = jwtHelper.generateToken(customUserDetailService.getUserDetail().getEmail(), customUserDetailService.getUserDetail().getRole());
                    return javaUtils.gettokenResponseEntity("Login Successfully", token, HttpStatus.OK);
                } else {
                    return javaUtils.getResponseEntity("User is inactive", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            log.error("{Error is : }", e);
        }
        return javaUtils.getResponseEntity("Bed Credentials", HttpStatus.BAD_REQUEST);
    }


    private Authentication authentication(String email, String password) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


    }

}
