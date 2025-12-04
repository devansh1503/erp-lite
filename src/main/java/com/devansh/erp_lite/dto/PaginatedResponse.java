package com.devansh.erp_lite.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private List<T> items;
    private int page;
    private int pageSize;
    private long total;
    private int totalPages;
}
