package com.devansh.erp_lite.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "uom_conversion_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UOMConversionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uom;
    private double conversionFactor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}