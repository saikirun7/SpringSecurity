package com.auth.SpringSecurity.service;

import com.auth.SpringSecurity.model.Users;
import com.auth.SpringSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService, UserService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserDetailsService(UserRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }

    @Override
    public Users addUser(Users user) {
        if (repo.findByUsername(user.getUsername()) != null) {
            throw new UsernameNotFoundException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }
}

