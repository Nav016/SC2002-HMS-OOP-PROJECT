package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import models.*;
import services.AuthenticationService;
import services.InventoryService;

public class DataInitializer {
    private static final String DOCTORS_CSV = "csv_files/Doctors.csv";
    private static final String PATIENTS_CSV = "csv_files/Patients.csv";
    private static final String PHARMACISTS_CSV = "csv_files/Pharmacists.csv";
    private static final String ADMINISTRATORS_CSV = "csv_files/Administrators.csv";
    private static final String MEDICINES_CSV = "csv_files/Medicines.csv";

    private final AuthenticationService authenticationService;
    private final InventoryService inventoryService;

    public DataInitializer(AuthenticationService authenticationService, InventoryService inventoryService) {
        this.authenticationService = authenticationService;
        this.inventoryService = inventoryService;
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
                if (data.length == 6) {
                    String hospitalID = data[0];
                    String password = data[1];
                    String name = data[2];
                    String email = data[3];
                    String phone = data[4];
                    String specialization = data[5];

                    // Create a new Doctor object and add it to the AuthenticationService
                    Doctor doctor = new Doctor(hospitalID, password, name, email, phone, specialization, null, null);
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
                if (data.length == 7) {
                    String hospitalID = data[0];
                    String password = data[1];
                    String name = data[2];
                    String email = data[3];
                    String phone = data[4];
                    String patientID = data[5];
                    String bloodType = data[6];

                    // Create a new Patient object and add it to the AuthenticationService
                    Patient patient = new Patient(hospitalID, password, name, email, phone, patientID, bloodType, null, null);
                    authenticationService.addUser(patient);
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
                if (data.length == 5) {
                    String hospitalID = data[0];
                    String password = data[1];
                    String name = data[2];
                    String email = data[3];
                    String phone = data[4];

                    User user = null;
                    switch (role) {
                        case "pharmacist" -> user = new Pharmacist(hospitalID, password, name, email, phone, null, null);
                        case "administrator" -> user = new Administrator(hospitalID, password, name, email, phone, null, null, null);
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
