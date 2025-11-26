package com.devansh.erp_lite.repositories;

import com.devansh.erp_lite.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepo extends JpaRepository<Warehouse, Long> {
}
