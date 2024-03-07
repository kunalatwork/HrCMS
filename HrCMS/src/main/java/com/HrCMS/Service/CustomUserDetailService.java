package com.HrCMS.Service;

import com.HrCMS.Dao.UserDao;
import com.HrCMS.Models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserDao userRepository;

   private com.HrCMS.Models.Users user;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user = userRepository.findByEmailId(username);

        if(user==null){
            throw new UsernameNotFoundException("User not found "+ username);
        }

       List<GrantedAuthority> authorityList = new ArrayList<>();

        return new User(user.getEmail(), user.getPassword(), authorityList);
    }

    public com.HrCMS.Models.Users getUserDetail(){
        return user;
    }
}
