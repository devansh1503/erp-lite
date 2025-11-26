package com.devansh.erp_lite.repositories;

import com.devansh.erp_lite.models.UOMConversionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UOMConversionDetailRepo extends JpaRepository<UOMConversionDetail, Long> {
}
