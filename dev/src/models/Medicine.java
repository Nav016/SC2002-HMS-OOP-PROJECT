package models;

import java.util.UUID;

public class Medicine {
    private String medicineID;
    private String name;
    private int stockLevel;
    private int lowStockAlert;

    // Constructor
    public Medicine(String name, int stockLevel, int lowStockAlert) {
        this.medicineID = UUID.randomUUID().toString(); // Generate a unique ID for each medicine
        this.name = name;
        this.stockLevel = stockLevel;
        this.lowStockAlert = lowStockAlert;
    }

    // Getters and Setters
    public String getMedicineID() {
        return medicineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    // Check if the current stock level is below the alert threshold
    public boolean isLowStock() {
        return stockLevel <= lowStockAlert;
    }

    @Override
    public String toString() {
        return "Medicine ID: " + medicineID +
               ", Name: " + name +
               ", Stock Level: " + stockLevel +
               ", Low Stock Alert: " + lowStockAlert;
    }
}
