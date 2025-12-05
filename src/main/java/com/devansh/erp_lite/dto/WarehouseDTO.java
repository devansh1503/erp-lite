package com.devansh.erp_lite.dto;

import lombok.Data;

@Data
public class WarehouseDTO {
    private Long id;
    private String warehouseName;
    private boolean disabled;
    private boolean isGroup;
    private boolean isRejectedWarehouse;
    private String company;
    private String account;
    private Integer lft;
    private Integer rgt;
    private String oldParent;
    private Long parentId;
    private String parentWarehouse;
}
