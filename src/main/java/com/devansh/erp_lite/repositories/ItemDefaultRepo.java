package com.devansh.erp_lite.repositories;

import com.devansh.erp_lite.models.ItemDefault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDefaultRepo extends JpaRepository<ItemDefault, Long> {
}
