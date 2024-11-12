package services;

import java.util.HashMap;
import java.util.Map;
import models.Medicine;

public class InventoryService {
    private Map<String, Medicine> inventory; // Stores medicine objects by medicineID
    private Map<String, Integer> replenishmentRequests; // Stores pending replenishment requests

    public InventoryService() {
        inventory = new HashMap<>();
        replenishmentRequests = new HashMap<>();
    }

    // View current inventory levels
    public void viewInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            for (Medicine medicine : inventory.values()) {
                System.out.println("Medicine ID: " + medicine.getMedicineID() + ", Name: " + medicine.getName() + ", Stock Level: " + medicine.getStockLevel() + ", Low Stock Alert: " + medicine.getLowStockAlert());
            }
        }
    }

    // Update stock level for a specific medicine
    public boolean updateInventory(String medicineID, int quantity) {
        Medicine medicine = inventory.get(medicineID);
        if (medicine != null) {
            medicine.setStockLevel(quantity);
            System.out.println("Inventory updated successfully for Medicine ID: " + medicineID);
            return true;
        } else {
            System.out.println("Medicine not found for ID: " + medicineID);
            return false;
        }
    }

    // Add a new medication to inventory
    public boolean addMedication(Medicine medicine) {
        if (inventory.containsKey(medicine.getMedicineID())) {
            System.out.println("Medication already exists in the inventory.");
            return false;
        }
        inventory.put(medicine.getMedicineID(), medicine);
        System.out.println("Medication added successfully.");
        return true;
    }

    // Remove a medication from the inventory
    public boolean removeMedication(String medicineID) {
        if (inventory.containsKey(medicineID)) {
            inventory.remove(medicineID);
            System.out.println("Medication removed successfully for Medicine ID: " + medicineID);
            return true;
        } else {
            System.out.println("Medicine not found for ID: " + medicineID);
            return false;
        }
    }

    // Submit a replenishment request
    public void submitReplenishmentRequest(String medicationID, int quantity) {
        if (inventory.containsKey(medicationID)) {
            replenishmentRequests.put(medicationID, replenishmentRequests.getOrDefault(medicationID, 0) + quantity);
            System.out.println("Replenishment request submitted for Medicine ID: " + medicationID + ", Quantity: " + quantity);
        } else {
            System.out.println("Medicine not found for ID: " + medicationID);
        }
    }

    // Approve replenishment request (handled by admin)
    public boolean approveReplenishmentRequest(String medicineID, int approvedQuantity) {
        if (replenishmentRequests.containsKey(medicineID)) {
            Medicine medicine = inventory.get(medicineID);
            if (medicine != null) {
                medicine.setStockLevel(medicine.getStockLevel() + approvedQuantity);
                replenishmentRequests.remove(medicineID);
                System.out.println("Replenishment approved and stock updated for Medicine ID: " + medicineID + ". New stock level: " + medicine.getStockLevel());
                return true;
            } else {
                System.out.println("Medicine not found for ID: " + medicineID);
            }
        } else {
            System.out.println("No pending replenishment request for Medicine ID: " + medicineID);
        }
        return false;
    }

    // View pending replenishment requests
    public void viewReplenishmentRequests() {
        if (replenishmentRequests.isEmpty()) {
            System.out.println("No pending replenishment requests.");
        } else {
            System.out.println("Pending Replenishment Requests:");
            for (Map.Entry<String, Integer> entry : replenishmentRequests.entrySet()) {
                String medicineID = entry.getKey();
                int quantity = entry.getValue();
                System.out.println("Medicine ID: " + medicineID + ", Requested Quantity: " + quantity);
            }
        }
    }
}
