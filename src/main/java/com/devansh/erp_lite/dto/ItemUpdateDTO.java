package com.devansh.erp_lite.dto;

import lombok.Data;

@Data
public class ItemUpdateDTO {
    private String itemCode;
    private String itemName;
    private String itemGroup;
    private String description;
    private String stockUom;
    private String gstHsnCode;

    private Boolean disabled;
    private Boolean isStockItem;
    private Boolean allowNegativeStock;

    private Double openingStock;
    private Double valuationRate;
    private Double standardRate;

    private Double weightPerUnit;

    private Boolean isPurchaseItem;
    private Integer leadTimeDays;
    private Double lastPurchaseRate;

    private Double minOrderQty;
    private Double safetyStock;
}
