package com.devansh.erp_lite.repositories;

import com.devansh.erp_lite.models.ItemTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTaxRepo extends JpaRepository<ItemTax, Long> {
}
