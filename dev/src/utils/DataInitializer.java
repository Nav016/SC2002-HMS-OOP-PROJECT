package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import models.*;
import services.AuthenticationService;
import services.InventoryService;
import services.MedicalRecordService;

public class DataInitializer {
    private static final String DOCTORS_CSV = "csv_files/Doctors.csv";
    private static final String PATIENTS_CSV = "csv_files/Patients.csv";
    private static final String PHARMACISTS_CSV = "csv_files/Pharmacists.csv";
    private static final String ADMINISTRATORS_CSV = "csv_files/Administrators.csv";
    private static final String MEDICINES_CSV = "csv_files/Medicines.csv";

    private final AuthenticationService authenticationService;
    private final InventoryService inventoryService;
    private final MedicalRecordService medicalRecordService;

    public DataInitializer(AuthenticationService authenticationService, InventoryService inventoryService, MedicalRecordService medicalRecordService) {
        this.authenticationService = authenticationService;
        this.inventoryService = inventoryService;
        this.medicalRecordService = medicalRecordService;
    }

    public void loadInitialData() {
        loadDoctors();
        loadPatients();
        loadUsersFromCSV(PHARMACISTS_CSV, "pharmacist");
        loadUsersFromCSV(ADMINISTRATORS_CSV, "administrator");
        loadMedicineInventory();
    }

    private void loadDoctors() {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTORS_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {
                    String hospitalID = data[0];
                    String password = data[1];
                    String name = data[2];
                    String email = data[3];
                    String phone = data[4];
                    String dob = data[5];
                    String gender = data[6];
                    String specialization = data[7];

                    // Create a new Doctor object and add it to the AuthenticationService
                    Doctor doctor = new Doctor(hospitalID, password, name, email, phone, dob, gender, specialization, null, null);
                    authenticationService.addUser(doctor);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading doctors from CSV: " + e.getMessage());
        }
    }

    private void loadPatients() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENTS_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 9) {
                    String hospitalID = data[0];
                    String password = data[1];
                    String name = data[2];
                    String email = data[3];
                    String phone = data[4];
                    String dob = data[5];
                    String gender = data[6];
                    String patientID = data[7];
                    String bloodType = data[8];
                    

                    // Create a new Patient object and add it to the AuthenticationService
                    Patient patient = new Patient(hospitalID, password, name, email, phone, dob, gender, patientID, bloodType,  null, null);
                    authenticationService.addUser(patient);
                    medicalRecordService.createMedicalRecord(patientID, name, dob, gender, patientID, email, bloodType);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading patients from CSV: " + e.getMessage());
        }
    }

    private void loadUsersFromCSV(String fileName, String role) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    String hospitalID = data[0];
                    String password = data[1];
                    String name = data[2];
                    String email = data[3];
                    String phone = data[4];
                    String dob = data[5];
                    String gender = data[6];

                    User user = null;
                    switch (role) {
                        case "pharmacist" -> user = new Pharmacist(hospitalID, password, name, email, phone, dob, gender, null, null);
                        case "administrator" -> user = new Administrator(hospitalID, password, name, email, phone, dob, gender, null, null, null);
                    }

                    if (user != null) {
                        authenticationService.addUser(user);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading " + role + " data from " + fileName + ": " + e.getMessage());
        }
    }
    private void loadMedicineInventory() {
        try (BufferedReader br = new BufferedReader(new FileReader(MEDICINES_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String name = data[0];
                    int stockLevel = Integer.parseInt(data[1]);
                    int lowStockAlert = Integer.parseInt(data[2]);

                    // Create a new Medicine object and add it to the InventoryService
                    Medicine medicine = new Medicine(name, stockLevel, lowStockAlert);
                    inventoryService.addMedication(medicine);
                }
            }
            System.out.println("Medicine inventory loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading medicines from CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error in medicine CSV format: " + e.getMessage());
        }
    }
}
