package com.devansh.erp_lite.dto;

import com.devansh.erp_lite.models.ItemTax;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class ItemDTO {
    private Long id;
    private String itemCode;

    private String itemName;
    private String itemGroup;
    private String description;

    private String stockUom;
    private String gstHsnCode;

    private boolean disabled;
    private boolean isStockItem;
    private boolean allowNegativeStock;

    private double openingStock;
    private double valuationRate;
    private double standardRate;

    private double weightPerUnit;

    private boolean isPurchaseItem;
    private int leadTimeDays;
    private double lastPurchaseRate;

    private double minOrderQty;
    private double safetyStock;

    private List<ItemTaxDTO> taxes;
}
