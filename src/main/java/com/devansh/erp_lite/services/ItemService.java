package com.devansh.erp_lite.services;

import com.devansh.erp_lite.dto.*;
import com.devansh.erp_lite.models.Item;
import com.devansh.erp_lite.models.ItemTax;
import com.devansh.erp_lite.repositories.ItemRepo;
import com.devansh.erp_lite.repositories.ItemTaxRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepo itemRepo;
    private final ItemTaxRepo itemTaxRepo;

    private ItemDTO createItemDTO(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setItemCode(item.getItemCode());
        dto.setItemName(item.getItemName());
        dto.setItemGroup(item.getItemGroup());
        dto.setDescription(item.getDescription());

        dto.setStockUom(item.getStockUom());
        dto.setGstHsnCode(item.getGstHsnCode());

        dto.setDisabled(item.isDisabled());
        dto.setStockItem(item.isStockItem());
        dto.setAllowNegativeStock(item.isAllowNegativeStock());

        dto.setOpeningStock(item.getOpeningStock());
        dto.setValuationRate(item.getValuationRate());
        dto.setStandardRate(item.getStandardRate());

        dto.setWeightPerUnit(item.getWeightPerUnit());

        dto.setPurchaseItem(item.isPurchaseItem());
        dto.setLeadTimeDays(item.getLeadTimeDays());
        dto.setLastPurchaseRate(item.getLastPurchaseRate());

        dto.setMinOrderQty(item.getMinOrderQty());
        dto.setSafetyStock(item.getSafetyStock());

        if(item.getTaxes() != null) {
            List<ItemTaxDTO> itemTaxDTOList = item.getTaxes().stream()
                    .map(this::createItemTaxDTO).toList();

            dto.setTaxes(itemTaxDTOList);
        }
        return dto;
    }

    private ItemTaxDTO createItemTaxDTO(ItemTax tax) {
        ItemTaxDTO taxDTO = new ItemTaxDTO();
        taxDTO.setId(tax.getId());
        taxDTO.setItemTaxTemplate(tax.getItemTaxTemplate());
        taxDTO.setMinimumNetRate(tax.getMinimumNetRate());
        taxDTO.setMaximumNetRate(tax.getMaximumNetRate());
        return taxDTO;
    }


    public ItemDTO createItem(String itemCode, String itemName, String itemGroup, String description, String stockUom, String gstHsnCode){
        Item item = new Item();
        item.setItemCode(itemCode);
        item.setItemName(itemName);
        item.setItemGroup(itemGroup);
        item.setDescription(description);
        item.setStockUom(stockUom);
        item.setGstHsnCode(gstHsnCode);
        item.setDisabled(false);

        itemRepo.save(item);
        return createItemDTO(item);
    }

    public ItemDTO updateItem(Long itemId, ItemUpdateDTO updateDTO) {
        Item item = itemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (updateDTO.getItemName() != null) item.setItemName(updateDTO.getItemName());
        if (updateDTO.getItemGroup() != null) item.setItemGroup(updateDTO.getItemGroup());
        if (updateDTO.getDescription() != null) item.setDescription(updateDTO.getDescription());
        if (updateDTO.getStockUom() != null) item.setStockUom(updateDTO.getStockUom());
        if (updateDTO.getGstHsnCode() != null) item.setGstHsnCode(updateDTO.getGstHsnCode());

        if (updateDTO.getDisabled() != null) item.setDisabled(updateDTO.getDisabled());
        if (updateDTO.getIsStockItem() != null) item.setStockItem(updateDTO.getIsStockItem());
        if (updateDTO.getAllowNegativeStock() != null) item.setAllowNegativeStock(updateDTO.getAllowNegativeStock());

        if (updateDTO.getOpeningStock() != null) item.setOpeningStock(updateDTO.getOpeningStock());
        if (updateDTO.getValuationRate() != null) item.setValuationRate(updateDTO.getValuationRate());
        if (updateDTO.getStandardRate() != null) item.setStandardRate(updateDTO.getStandardRate());

        if (updateDTO.getWeightPerUnit() != null) item.setWeightPerUnit(updateDTO.getWeightPerUnit());

        if (updateDTO.getIsPurchaseItem() != null) item.setPurchaseItem(updateDTO.getIsPurchaseItem());
        if (updateDTO.getLeadTimeDays() != null) item.setLeadTimeDays(updateDTO.getLeadTimeDays());
        if (updateDTO.getLastPurchaseRate() != null) item.setLastPurchaseRate(updateDTO.getLastPurchaseRate());

        if (updateDTO.getMinOrderQty() != null) item.setMinOrderQty(updateDTO.getMinOrderQty());
        if (updateDTO.getSafetyStock() != null) item.setSafetyStock(updateDTO.getSafetyStock());

        itemRepo.save(item);
        return createItemDTO(item);
    }

    public ItemDTO createItemTax(Long itemId, String itemTaxTemplate, double minimumNetRate, double maximumNetRate){
        ItemTax itemTax = new ItemTax();
        itemTax.setItemTaxTemplate(itemTaxTemplate);
        itemTax.setMinimumNetRate(minimumNetRate);
        itemTax.setMaximumNetRate(maximumNetRate);
        itemTaxRepo.save(itemTax);

        Item item = itemRepo.findById(itemId).get();
        item.getTaxes().add(itemTax);

        return createItemDTO(item);
    }

    public ItemDTO updateItemTax(Long itemId, Long taxId, ItemTaxUpdateDTO dto) {
        Item item = itemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        ItemTax tax = itemTaxRepo.findById(taxId)
                .orElseThrow(() -> new RuntimeException("Item tax not found"));

        if (dto.getItemTaxTemplate() != null) tax.setItemTaxTemplate(dto.getItemTaxTemplate());
        if (dto.getMinimumNetRate() != null) tax.setMinimumNetRate(dto.getMinimumNetRate());
        if (dto.getMaximumNetRate() != null) tax.setMaximumNetRate(dto.getMaximumNetRate());

        itemTaxRepo.save(tax);
        return createItemDTO(item);
    }

    public PaginatedResponse<ItemDTO> getPaginateItems(
            int page,
            int size,
            String search,
            String itemGroup,
            Boolean isStockItem,
            Boolean isPurchaseItem,
            Boolean disabled
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("itemName").ascending());
        Specification<Item> spec = ItemSpecification.filter(search, itemGroup, isStockItem, isPurchaseItem, disabled);
        Page<Item> itemPage = itemRepo.findAll(spec, pageable);

        List<ItemDTO> dtoList = itemPage.getContent().stream()
                .map(this::createItemDTO)
                .collect(Collectors.toList());

        PaginatedResponse<ItemDTO> response = new PaginatedResponse<>();
        response.setItems(dtoList);
        response.setPage(itemPage.getNumber());
        response.setTotalPages(itemPage.getTotalPages());
        response.setTotal(itemPage.getTotalElements());
        response.setPageSize(itemPage.getSize());

        return response;
    }
}



class ItemSpecification{
    public static Specification<Item> filter(
            String search,
            String itemGroup,
            Boolean isStockItem,
            Boolean isPurchaseItem,
            Boolean disabled
    ){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(search != null && !search.isEmpty()){
                String like = "%" + search.toLowerCase() + "%";
                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("itemCode")), like),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("itemName")), like),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("itemGroup")), like)
                        )
                );
            }

            if(itemGroup != null) predicates.add(criteriaBuilder.equal(root.get("itemGroup"), itemGroup));
            if(isStockItem != null) predicates.add(criteriaBuilder.equal(root.get("isStockItem"), isStockItem));
            if(isPurchaseItem != null) predicates.add(criteriaBuilder.equal(root.get("isPurchaseItem"), isPurchaseItem));
            if(disabled != null) predicates.add(criteriaBuilder.equal(root.get("disabled"), disabled));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
