package com.devansh.erp_lite.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String itemCode;

    private String itemName;
    private String itemGroup;
    private String gstHsnCode;

    private String stockUom;
    private String profitCenter;
    private String itemType;

    private boolean disabled;
    private boolean allowAlternativeItem;
    private boolean isStockItem;
    private boolean hasVariants;

    private double openingStock;
    private double valuationRate;
    private double standardRate;

    private boolean isFixedAsset;
    private boolean autoCreateAssets;
    private boolean isGroupedAsset;

    private double overDeliveryReceiptAllowance;
    private double overBillingAllowance;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int shelfLifeInDays;

    private LocalDate endOfLife;

    private String defaultMaterialRequestType;

    private double weightPerUnit;
    private boolean allowNegativeStock;

    private boolean hasBatchNo;
    private boolean createNewBatch;

    private boolean hasExpiryDate;
    private boolean retainSample;
    private double sampleQuantity;

    private boolean hasSerialNo;

    private String variantBasedOn;

    private boolean enableDeferredExpense;
    private int noOfMonthsExp;

    private boolean enableDeferredRevenue;
    private int noOfMonths;

    private double minOrderQty;
    private double safetyStock;

    private boolean isPurchaseItem;
    private int leadTimeDays;
    private double lastPurchaseRate;

    private boolean isCustomerProvidedItem;
    private boolean deliveredBySupplier;

    private String countryOfOrigin;
    private boolean grantCommission;

    private boolean isSalesItem;
    private double maxDiscount;

    private boolean isIneligibleForItc;

    private boolean inspectionRequiredBeforePurchase;
    private boolean inspectionRequiredBeforeDelivery;

    private boolean includeItemInManufacturing;
    private boolean isSubContractedItem;

    private String customerCode;

    private double totalProjectedQty;

    // ------------------------
    //  RELATIONSHIPS
    // ------------------------

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemTax> taxes;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemDefault> itemDefaults;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UOMConversionDetail> uoms;

}