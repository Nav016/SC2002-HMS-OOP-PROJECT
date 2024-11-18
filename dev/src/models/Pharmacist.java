package models;

import services.InventoryService;
import services.PrescriptionService;

public class Pharmacist extends User {
    private PrescriptionService prescriptionService;
    private InventoryService inventoryService;

    // Constructor
    public Pharmacist(String hospitalID, String password, String name, String email, String phone, String dob, String gender,
                      PrescriptionService prescriptionService, InventoryService inventoryService) {
        super(hospitalID, password, name, email, phone, dob, gender);
        this.prescriptionService = prescriptionService;
        this.inventoryService = inventoryService;
    }

    // Role-specific methods
    public void viewPrescription(String prescriptionID) {
        prescriptionService.viewPrescription(prescriptionID);
    }

    public void updatePrescriptionStatus(String prescriptionID, String status) {
        prescriptionService.updatePrescriptionStatus(prescriptionID, status);
    }

    public void viewMedicationInventory() {
        inventoryService.viewInventory();
    }

    public void submitReplenishmentRequest(String medicationID, int quantity) {
        inventoryService.submitReplenishmentRequest(medicationID, quantity);
    }

    @Override
    public void showMenu() {
        System.out.println("Pharmacist Menu:");
        System.out.println("1. View Prescription");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");
    }
}
