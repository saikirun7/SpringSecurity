package com.auth.SpringSecurity.repository;

import com.auth.SpringSecurity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
