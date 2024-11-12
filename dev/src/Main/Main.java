package Main;

import java.util.Scanner;
import models.*;
import services.*;
import utils.DataInitializer;

public class Main {
    private static AuthenticationService authenticationService = new AuthenticationService();
    private static InventoryService inventoryService = new InventoryService();
    private static StaffService staffService = new StaffService();
    private static AppointmentService appointmentService = new AppointmentService();
    private static MedicalRecordService medicalRecordService = new MedicalRecordService();
    private static PrescriptionService prescriptionService = new PrescriptionService();
    private static DataInitializer dataInitializer = new DataInitializer(authenticationService, inventoryService);

    public static void main(String[] args) {
        System.out.println("Welcome to the Hospital Management System (HMS)");

        // Load initial data from CSV files
        dataInitializer.loadInitialData();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1. Sign In");
            System.out.println("2. Create a New Account");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (choice == 1) {
                if (signIn(scanner)) {
                    break; // Exit the loop once signed in
                }
            } else if (choice == 2) {
                createNewAccount(scanner);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static boolean signIn(Scanner scanner) {
        System.out.print("Enter your Hospital ID: ");
        String hospitalID = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = authenticationService.authenticate(hospitalID, password);
        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getName());

            // Provide role-specific access
            switch (user.getClass().getSimpleName()) {
                case "Patient" -> patientMenu(scanner);
                case "Doctor" -> doctorMenu(scanner);
                case "Pharmacist" -> pharmacistMenu(scanner);
                case "Administrator" -> administratorMenu(scanner);
                default -> {
                    System.out.println("Invalid role. Access denied.");
                    return false;
                }
            }
            return true; // Sign-in successful
        } else {
            System.out.println("Invalid Hospital ID or password. Please try again.");
            return false; // Sign-in failed
        }
    }

    private static void createNewAccount(Scanner scanner) {
        System.out.println("Create a New Account:");
        System.out.print("Enter role (doctor/patient/pharmacist/administrator): ");
        String role = scanner.nextLine().toLowerCase();

        System.out.print("Enter Hospital ID: ");
        String hospitalID = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        String additionalInfo1 = null, additionalInfo2 = null;
        if ("doctor".equals(role)) {
            System.out.print("Enter specialization: ");
            additionalInfo1 = scanner.nextLine();
        } else if ("patient".equals(role)) {
            System.out.print("Enter patient ID: ");
            additionalInfo1 = scanner.nextLine();
            System.out.print("Enter blood type: ");
            additionalInfo2 = scanner.nextLine();
        }

        User newUser = authenticationService.createUser(role, hospitalID, password, name, email, phone, additionalInfo1, additionalInfo2);
        if (newUser != null) {
            System.out.println("Account created successfully. You can now sign in.");
        } else {
            System.out.println("Failed to create account. Please try again.");
        }
    }

    // Patient Menu
    private static void patientMenu(Scanner scanner) {
        System.out.println("Welcome, Patient");
        boolean running = true;
        while (running) {
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> medicalRecordService.viewMedicalRecords(scanner.nextLine());
                case 2 -> {
                    System.out.print("Enter new email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter new phone: ");
                    String phone = scanner.nextLine();
                    // Update personal info (method would need to be implemented)
                }
                case 3 -> appointmentService.viewAvailableAppointments(scanner.nextLine());
                case 4 -> {
                    System.out.print("Enter Doctor ID: ");
                    String doctorID = scanner.nextLine();
                    System.out.print("Enter date (yyyy-mm-dd): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter time (hh:mm): ");
                    String time = scanner.nextLine();
                    appointmentService.scheduleAppointment(scanner.nextLine(), doctorID, date, time);
                }
                case 5 -> {
                    System.out.print("Enter Appointment ID: ");
                    String appointmentID = scanner.nextLine();
                    System.out.print("Enter new date (yyyy-mm-dd): ");
                    String newDate = scanner.nextLine();
                    System.out.print("Enter new time (hh:mm): ");
                    String newTime = scanner.nextLine();
                    appointmentService.rescheduleAppointment(appointmentID, newDate, newTime);
                }
                case 6 -> {
                    System.out.print("Enter Appointment ID: ");
                    appointmentService.cancelAppointment(scanner.nextLine());
                }
                case 7 -> appointmentService.viewPatientAppointments(scanner.nextLine());
                case 8 -> medicalRecordService.viewPastAppointments(scanner.nextLine());
                case 9 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // Doctor Menu
    private static void doctorMenu(Scanner scanner) {
        System.out.println("Welcome, Doctor");
        boolean running = true;
        while (running) {
            System.out.println("Doctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> viewPatientMedicalRecords(scanner);
                case 2 -> updatePatientMedicalRecords(scanner);
                case 3 -> viewPersonalSchedule();
                case 4 -> setAvailability(scanner);
                case 5 -> handleAppointmentRequests(scanner);
                case 6 -> viewUpcomingAppointments();
                case 7 -> recordAppointmentOutcome(scanner);
                case 8 -> running = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // 1. View Patient Medical Records
    private static void viewPatientMedicalRecords(Scanner scanner) {
        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();
        medicalRecordService.viewMedicalRecords(patientID);
    }

    // 2. Update Patient Medical Records
    private static void updatePatientMedicalRecords(Scanner scanner) {
        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();
        System.out.print("Enter Diagnosis: ");
        String diagnosis = scanner.nextLine();
        System.out.print("Enter Treatment: ");
        String treatment = scanner.nextLine();
        medicalRecordService.updateMedicalRecord(patientID, diagnosis, treatment);
        System.out.println("Medical record updated successfully.");
    }

    // 3. View Personal Schedule
    private static void viewPersonalSchedule() {
        // Assuming doctorID is linked to current session or login
        System.out.print("Enter Doctor ID: ");
        String doctorID = new Scanner(System.in).nextLine(); // Replace with actual doctor ID from session if
                                                             // implemented
        appointmentService.viewDoctorAppointments(doctorID);
    }

    // 4. Set Availability for Appointments
    private static void setAvailability(Scanner scanner) {
        System.out.print("Enter Doctor ID: ");
        String doctorID = scanner.nextLine();
        System.out.print("Enter available date (yyyy-mm-dd): ");
        String date = scanner.nextLine();
        System.out.print("Enter available time (hh:mm): ");
        String time = scanner.nextLine();
        appointmentService.setDoctorAvailability(doctorID, date, time);
        System.out.println("Availability set successfully.");
    }

    // 5. Accept or Decline Appointment Requests
    private static void handleAppointmentRequests(Scanner scanner) {
        System.out.print("Enter Appointment ID: ");
        String appointmentID = scanner.nextLine();
        System.out.print("Enter 'accept' to accept or 'decline' to decline: ");
        String decision = scanner.nextLine().toLowerCase();
        if (decision.equals("accept")) {
            appointmentService.acceptAppointment(appointmentID);
            System.out.println("Appointment accepted.");
        } else if (decision.equals("decline")) {
            appointmentService.declineAppointment(appointmentID);
            System.out.println("Appointment declined.");
        } else {
            System.out.println("Invalid decision. Please enter 'accept' or 'decline'.");
        }
    }

    // 6. View Upcoming Appointments
    private static void viewUpcomingAppointments() {
        // Assuming doctorID is linked to current session or login
        System.out.print("Enter Doctor ID: ");
        String doctorID = new Scanner(System.in).nextLine(); // Replace with actual doctor ID from session if
                                                             // implemented
        appointmentService.viewDoctorAppointments(doctorID);
    }

    // 7. Record Appointment Outcome
    private static void recordAppointmentOutcome(Scanner scanner) {
        System.out.print("Enter Appointment ID: ");
        String appointmentID = scanner.nextLine();
        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();
        System.out.print("Enter Service Type (e.g., Consultation, X-ray): ");
        String serviceType = scanner.nextLine();
        System.out.print("Enter Consultation Notes: ");
        String notes = scanner.nextLine();
        System.out.print("Enter Medication Prescribed: ");
        String medication = scanner.nextLine();
        medicalRecordService.recordAppointmentOutcome(appointmentID, patientID, serviceType, notes, medication);
        System.out.println("Appointment outcome recorded successfully.");
    }

    // Pharmacist Menu
    private static void pharmacistMenu(Scanner scanner) {
        System.out.println("Welcome, Pharmacist");
        boolean running = true;
        while (running) {
            System.out.println("Pharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> viewAppointmentOutcomeRecord(scanner);
                case 2 -> updatePrescriptionStatus(scanner);
                case 3 -> viewMedicationInventory();
                case 4 -> submitReplenishmentRequest(scanner);
                case 5 -> running = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // 1. View Appointment Outcome Record
    private static void viewAppointmentOutcomeRecord(Scanner scanner) {
        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();
        medicalRecordService.viewPastAppointments(patientID);
    }

    // 2. Update Prescription Status
    private static void updatePrescriptionStatus(Scanner scanner) {
        System.out.print("Enter Prescription ID: ");
        String prescriptionID = scanner.nextLine();
        System.out.print("Enter new status (e.g., 'Dispensed'): ");
        String status = scanner.nextLine();
        prescriptionService.updatePrescriptionStatus(prescriptionID, status);
        System.out.println("Prescription status updated successfully.");
    }

    // 3. View Medication Inventory
    private static void viewMedicationInventory() {
        inventoryService.viewInventory();
    }

    // 4. Submit Replenishment Request
    private static void submitReplenishmentRequest(Scanner scanner) {
        System.out.print("Enter Medicine ID: ");
        String medicineID = scanner.nextLine();
        System.out.print("Enter quantity to request: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        inventoryService.submitReplenishmentRequest(medicineID, quantity);
        System.out.println("Replenishment request submitted.");
    }

    // Administrator Menu
    private static void administratorMenu(Scanner scanner) {
        System.out.println("Welcome, Administrator");
        boolean running = true;
        while (running) {
            System.out.println("Administrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointment Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> manageHospitalStaff(scanner);
                case 2 -> viewAppointmentDetails();
                case 3 -> manageMedicationInventory(scanner);
                case 4 -> approveReplenishmentRequest(scanner);
                case 5 -> running = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // 1. View and Manage Hospital Staff
    private static void manageHospitalStaff(Scanner scanner) {
        boolean managingStaff = true;
        while (managingStaff) {
            System.out.println("Manage Hospital Staff:");
            System.out.println("1. Add Staff Member");
            System.out.println("2. Update Staff Member");
            System.out.println("3. Remove Staff Member");
            System.out.println("4. View All Staff");
            System.out.println("5. Back to Administrator Menu");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addStaffMember(scanner);
                case 2 -> updateStaffMember(scanner);
                case 3 -> removeStaffMember(scanner);
                case 4 -> staffService.viewAllStaff();
                case 5 -> managingStaff = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Add a new staff member
    private static void addStaffMember(Scanner scanner) {
        System.out.print("Enter Hospital ID: ");
        String hospitalID = scanner.nextLine();
        System.out.print("Enter Role (Doctor, Pharmacist, Administrator): ");
        String role = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        String specialization = null;

        User user = switch (role.toLowerCase()) {
            case "doctor" -> {
                System.out.print("Enter Specialization: ");
                specialization = scanner.nextLine();
                yield new Doctor(hospitalID, "password", name, email, phone, specialization, appointmentService,
                        medicalRecordService);
            }
            case "pharmacist" ->
                new Pharmacist(hospitalID, "password", name, email, phone, prescriptionService, inventoryService);
            case "administrator" -> new Administrator(hospitalID, "password", name, email, phone, staffService,
                    inventoryService, appointmentService);
            default -> null;
        };

        if (user != null) {
            staffService.addStaffMember(user);
            authenticationService.addUser(user);
            System.out.println("Staff member added successfully.");
        } else {
            System.out.println("Invalid role entered. Staff member not added.");
        }
    }

    // Update an existing staff member
    private static void updateStaffMember(Scanner scanner) {
        System.out.print("Enter Hospital ID of staff to update: ");
        String hospitalID = scanner.nextLine();
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new phone: ");
        String phone = scanner.nextLine();

        User updatedStaff = staffService.getStaffByID(hospitalID);
        if (updatedStaff != null) {
            updatedStaff.setName(name);
            updatedStaff.setEmail(email);
            updatedStaff.setPhone(phone);
            staffService.updateStaffMember(hospitalID, updatedStaff);
            System.out.println("Staff member updated successfully.");
        } else {
            System.out.println("Staff member not found.");
        }
    }

    // Remove a staff member
    private static void removeStaffMember(Scanner scanner) {
        System.out.print("Enter Hospital ID of staff to remove: ");
        String hospitalID = scanner.nextLine();
        if (staffService.removeStaffMember(hospitalID)) {
            System.out.println("Staff member removed successfully.");
        } else {
            System.out.println("Staff member not found.");
        }
    }

    // 2. View Appointment Details
    private static void viewAppointmentDetails() {
        System.out.println("Viewing all appointment details:");
        // Assuming administrator has access to view all appointments
        appointmentService.viewAllAppointments();
    }

    // 3. View and Manage Medication Inventory
    private static void manageMedicationInventory(Scanner scanner) {
        boolean managingInventory = true;
        while (managingInventory) {
            System.out.println("Manage Medication Inventory:");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Medication");
            System.out.println("3. Update Medication Stock");
            System.out.println("4. Remove Medication");
            System.out.println("5. Back to Administrator Menu");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> inventoryService.viewInventory();
                case 2 -> addMedication(scanner);
                case 3 -> updateMedicationStock(scanner);
                case 4 -> removeMedication(scanner);
                case 5 -> managingInventory = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Add a new medication to inventory
    private static void addMedication(Scanner scanner) {
        System.out.print("Enter Medicine Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Stock Level: ");
        int stockLevel = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Low Stock Alert Level: ");
        int lowStockAlert = Integer.parseInt(scanner.nextLine());

        Medicine medicine = new Medicine(name, stockLevel, lowStockAlert);
        inventoryService.updateInventory(medicine.getMedicineID(), stockLevel);
        System.out.println("Medication added successfully.");
    }

    // Update the stock level of an existing medication
    private static void updateMedicationStock(Scanner scanner) {
        System.out.print("Enter Medicine ID: ");
        String medicineID = scanner.nextLine();
        System.out.print("Enter new stock level: ");
        int stockLevel = Integer.parseInt(scanner.nextLine());
        if (inventoryService.updateInventory(medicineID, stockLevel)) {
            System.out.println("Medication stock updated successfully.");
        } else {
            System.out.println("Medicine not found.");
        }
    }

    // Remove a medication from inventory
    private static void removeMedication(Scanner scanner) {
        System.out.print("Enter Medicine ID: ");
        String medicineID = scanner.nextLine();
        if (inventoryService.removeMedication(medicineID)) {
            System.out.println("Medication removed successfully.");
        } else {
            System.out.println("Medicine not found.");
        }
    }

    // 4. Approve Replenishment Requests
    private static void approveReplenishmentRequest(Scanner scanner) {
        System.out.print("Enter Medicine ID for replenishment approval: ");
        String medicineID = scanner.nextLine();
        System.out.print("Enter quantity to approve: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        if (inventoryService.approveReplenishmentRequest(medicineID, quantity)) {
            System.out.println("Replenishment request approved and stock updated.");
        } else {
            System.out.println("Medicine not found or request could not be approved.");
        }
    }

}
