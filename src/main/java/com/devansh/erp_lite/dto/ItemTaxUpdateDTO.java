package com.devansh.erp_lite.dto;

import lombok.Data;

@Data
public class ItemTaxUpdateDTO {
    private String itemTaxTemplate;
    private Double minimumNetRate;
    private Double maximumNetRate;
}