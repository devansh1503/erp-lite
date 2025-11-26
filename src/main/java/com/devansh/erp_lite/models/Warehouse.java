package com.devansh.erp_lite.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String warehouseName;

    private boolean disabled;
    private boolean isGroup;
    private boolean isRejectedWarehouse;

    private String company;
    private String account;

    // Nested Set model fields (used for tree structure)
    private Integer lft;
    private Integer rgt;

    private String oldParent;

    // Self-referencing parent (recursive tree)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_warehouse_id")
    private Warehouse parentWarehouse;
}