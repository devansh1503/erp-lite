package com.devansh.erp_lite.repositories;

import com.devansh.erp_lite.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
}
