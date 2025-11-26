package com.devansh.erp_lite.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_taxes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemTax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemTaxTemplate;

    private double minimumNetRate;
    private double maximumNetRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
