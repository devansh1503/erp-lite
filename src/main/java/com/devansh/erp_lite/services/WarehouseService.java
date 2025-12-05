package com.devansh.erp_lite.services;

import com.devansh.erp_lite.dto.PaginatedResponse;
import com.devansh.erp_lite.dto.WarehouseDTO;
import com.devansh.erp_lite.models.Warehouse;
import com.devansh.erp_lite.repositories.WarehouseRepo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepo warehouseRepo;


    public WarehouseDTO createWarehouseDTO(Warehouse warehouse) {
        if (warehouse == null) {
            return null;
        }

        WarehouseDTO dto = new WarehouseDTO();
        dto.setId(warehouse.getId());
        dto.setWarehouseName(warehouse.getWarehouseName());
        dto.setDisabled(warehouse.isDisabled());

        // Lombok boolean naming: field "isGroup" -> setter is setGroup(...)
        dto.setGroup(warehouse.isGroup());
        dto.setRejectedWarehouse(warehouse.isRejectedWarehouse());

        dto.setCompany(warehouse.getCompany());
        dto.setAccount(warehouse.getAccount());

        dto.setLft(warehouse.getLft());
        dto.setRgt(warehouse.getRgt());
        dto.setOldParent(warehouse.getOldParent());

        Warehouse parent = warehouse.getParentWarehouse();
        if (parent != null) {
            dto.setParentId(parent.getId());
            dto.setParentWarehouse(parent.getWarehouseName());
        }

        return dto;
    }

    public WarehouseDTO createWarehouse(WarehouseDTO requestData, Long parentId) {

        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseName(requestData.getWarehouseName());
        warehouse.setDisabled(requestData.isDisabled());
        warehouse.setGroup(requestData.isGroup());
        warehouse.setRejectedWarehouse(requestData.isRejectedWarehouse());
        warehouse.setCompany(requestData.getCompany());
        warehouse.setAccount(requestData.getAccount());
        warehouse.setLft(requestData.getLft());
        warehouse.setRgt(requestData.getRgt());
        warehouse.setOldParent(requestData.getOldParent());

        if (parentId != null) {
            Warehouse parent = warehouseRepo.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent warehouse not found: " + parentId));
            warehouse.setParentWarehouse(parent);
        }

        Warehouse saved = warehouseRepo.save(warehouse);
        return createWarehouseDTO(saved);
    }

    public WarehouseDTO updateWarehouse(Long id, Warehouse warehouseData) {

        Warehouse warehouse = warehouseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found: " + id));

        warehouse.setWarehouseName(warehouseData.getWarehouseName());
        warehouse.setDisabled(warehouseData.isDisabled());
        warehouse.setGroup(warehouseData.isGroup());
        warehouse.setRejectedWarehouse(warehouseData.isRejectedWarehouse());
        warehouse.setCompany(warehouseData.getCompany());
        warehouse.setAccount(warehouseData.getAccount());
        warehouse.setLft(warehouseData.getLft());
        warehouse.setRgt(warehouseData.getRgt());
        warehouse.setOldParent(warehouseData.getOldParent());

        // If parent is provided in the update request
        if (warehouseData.getParentWarehouse() != null) {
            Long parentId = warehouseData.getParentWarehouse().getId();
            Warehouse parent = warehouseRepo.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent warehouse not found: " + parentId));
            warehouse.setParentWarehouse(parent);
        }

        Warehouse updated = warehouseRepo.save(warehouse);
        return createWarehouseDTO(updated);
    }

    public PaginatedResponse<WarehouseDTO> getPaginatedWarehouse(
            int page,
            int size,
            String search,
            String company,
            Boolean disabled,
            Boolean isGroup,
            Boolean isRejectedWarehouse
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("warehouseName").ascending());
        Specification<Warehouse> spec = WarehouseSpecifications.filter(search, company, disabled, isGroup, isRejectedWarehouse);
        Page<Warehouse> warehousePage = warehouseRepo.findAll(spec, pageable);

        List<WarehouseDTO> warehouseDTOList = warehousePage.getContent().stream()
                .map(this::createWarehouseDTO).toList();

        PaginatedResponse<WarehouseDTO> response = new PaginatedResponse<>();
        response.setItems(warehouseDTOList);
        response.setTotal(warehousePage.getTotalElements());
        response.setPage(warehousePage.getNumber());
        response.setTotalPages(warehousePage.getTotalPages());
        response.setPageSize(warehousePage.getSize());

        return response;
    }
}

class WarehouseSpecifications {
    public static Specification<Warehouse> filter(
            String search,
            String company,
            Boolean disabled,
            Boolean isGroup,
            Boolean isRejectedWarehouse
    ){
        return (root, query, cb)->{
            List<Predicate> predicates = new ArrayList<>();

            if(search!=null && !search.isEmpty()){
                String like = "%"+search.toLowerCase()+"%";
                predicates.add(cb.like(root.get("warehouseName"),like));
            }
            if(company!=null && !company.isEmpty()) predicates.add(cb.equal(root.get("company"), company));
            if(disabled!=null) predicates.add(cb.equal(root.get("disabled"), disabled));
            if(isGroup!=null) predicates.add(cb.equal(root.get("isGroup"), isGroup));
            if(isRejectedWarehouse!=null) predicates.add(cb.equal(root.get("isRejectedWarehouse"), isRejectedWarehouse));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}