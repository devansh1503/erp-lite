package com.devansh.erp_lite.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_defaults")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDefault {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    private String defaultDiscountAccount;
    private String expenseAccount;
    private String incomeAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}