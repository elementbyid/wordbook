package com.jgji.spring.domain.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jgji.spring.domain.user.model.User;
import com.jgji.spring.domain.user.model.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository UserRepository;
    
    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserRepository.findByUserName(username);
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
    
    public User save(User user) {
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        
        return UserRepository.save(user);
    }
    
    
    public String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    
    public User getUserByUserName(String username) {
        User user = UserRepository.findByUserName(username);
        return user;
    }
    
    public String getUserIdByUserName(String userName) {
        User user = UserRepository.findByUserName(userName);
        return user.getId();
    }

}
