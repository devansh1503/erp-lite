package com.devansh.erp_lite.dto;

import lombok.Data;

@Data
public class ItemTaxDTO {
    private Long id;
    private String itemTaxTemplate;
    private double minimumNetRate;
    private double maximumNetRate;
}
