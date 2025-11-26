package com.devansh.erp_lite.repositories;

import com.devansh.erp_lite.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}
