package models;

import services.AppointmentService;
import services.InventoryService;
import services.StaffService;

public class Administrator extends User {
    private StaffService staffService;
    private InventoryService inventoryService;
    private AppointmentService appointmentService;

    // Constructor
    public Administrator(String hospitalID, String password, String name, String email, String phone, String dob, String gender,
                         StaffService staffService, InventoryService inventoryService, AppointmentService appointmentService) {
        super(hospitalID, password, name, email, phone, dob, gender);
        this.staffService = staffService;
        this.inventoryService = inventoryService;
        this.appointmentService = appointmentService;
    }

    // Role-specific methods
    public void manageStaff(String action, User staff) {
        staffService.manageStaff(action, staff);
    }

    public void viewAppointments() {
        appointmentService.viewAllAppointments();
    }

    public void manageInventory(String action, Medicine medicine) {
        inventoryService.viewReplenishmentRequests();
    }

    public void approveReplenishmentRequest(String medicineID, int quantity) {
        inventoryService.approveReplenishmentRequest(medicineID, quantity);
    }

    @Override
    public void showMenu() {
        System.out.println("Administrator Menu:");
        System.out.println("1. Manage Staff");
        System.out.println("2. View Appointments");
        System.out.println("3. View Replenishment Requests");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }
}
