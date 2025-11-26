package com.devansh.erp_lite.repositories;

import com.devansh.erp_lite.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    public Role findByRole(String role);
}
