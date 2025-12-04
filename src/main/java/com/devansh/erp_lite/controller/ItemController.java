package com.devansh.erp_lite.controller;

import com.devansh.erp_lite.dto.ItemDTO;
import com.devansh.erp_lite.dto.ItemTaxUpdateDTO;
import com.devansh.erp_lite.dto.ItemUpdateDTO;
import com.devansh.erp_lite.dto.PaginatedResponse;
import com.devansh.erp_lite.services.ItemService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/item")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/get")
    public PaginatedResponse<ItemDTO> getItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String itemGroup,
            @RequestParam(required = false) Boolean isStockItem,
            @RequestParam(required = false) Boolean isPurchaseItem,
            @RequestParam(required = false) Boolean disabled
    ){
        return itemService.getPaginateItems(
                page, size, search, itemGroup, isStockItem, isPurchaseItem, disabled
        );
    }


    @PostMapping("/create")
    public ItemDTO createItem(@RequestBody ItemUpdateDTO itemUpdateDTO){
        return itemService.createItem(
                itemUpdateDTO.getItemCode(),
                itemUpdateDTO.getItemName(),
                itemUpdateDTO.getItemGroup(),
                itemUpdateDTO.getDescription(),
                itemUpdateDTO.getStockUom(),
                itemUpdateDTO.getGstHsnCode()
        );
    }

    @PostMapping("/update/{itemId}")
    public ItemDTO updateItem(@PathVariable String itemId, @RequestBody ItemUpdateDTO itemUpdateDTO){
        return itemService.updateItem(Long.parseLong(itemId), itemUpdateDTO);
    }

    @PostMapping("/create-tax/{itemId}")
    public ItemDTO createItemTax(@PathVariable String itemId, @RequestBody ItemTaxUpdateDTO itemTaxUpdateDTO){
        return itemService.createItemTax(
                Long.parseLong(itemId),
                itemTaxUpdateDTO.getItemTaxTemplate(),
                itemTaxUpdateDTO.getMinimumNetRate(),
                itemTaxUpdateDTO.getMaximumNetRate()
        );
    }

    @PostMapping("/update-tax/{itemId}/{taxId}")
    public ItemDTO updateItemTax(
            @PathVariable String itemId,
            @PathVariable String taxId,
            @RequestBody ItemTaxUpdateDTO itemTaxUpdateDTO){
        return itemService.updateItemTax(Long.parseLong(itemId), Long.parseLong(taxId), itemTaxUpdateDTO);
    }
}
