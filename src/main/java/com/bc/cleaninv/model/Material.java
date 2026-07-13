package com.bc.cleaninv.model;

public class Material {

    private int materialId;
    private String name;
    private String description;
    private String unit;
    private int quantity;
    private int reorderLevel;
    private Integer supplierId;   // nullable FK
    private String supplierName;  // populated by joins for display only

    public Material() {
    }

    public Material(int materialId, String name, String description, String unit,
                     int quantity, int reorderLevel, Integer supplierId) {
        this.materialId = materialId;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.supplierId = supplierId;
    }

    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }

    public Integer getSupplierId() { return supplierId; }
    public void setSupplierId(Integer supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    /** Business rule: low stock when quantity has reached or dropped below reorder level. */
    public boolean isLowStock() {
        return quantity <= reorderLevel;
    }
}
