package com.devansh.erp_lite.repositories;

import com.devansh.erp_lite.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Long> {
}
