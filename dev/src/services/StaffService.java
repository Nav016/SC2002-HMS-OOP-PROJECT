package services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import models.Administrator;
import models.Doctor;
import models.Pharmacist;
import models.User;



public class StaffService {
    private List<User> staffList;

    private static final String DOCTORS_CSV = "csv_files/Doctors.csv";
    private static final String ADMINISTRATORS_CSV = "csv_files/Administrators.csv";
    private static final String PHARMACISTS_CSV = "csv_files/Pharmacists.csv";

    public StaffService() {
        staffList = new ArrayList<>();
        loadStaffFromCSV(); // Load staff data when the service is instantiated
    }

    // Load all staff members from CSV files
    private void loadStaffFromCSV() {
        loadDoctors();
        loadAdministrators();
        loadPharmacists();
    }

    // Load doctors from CSV
    private void loadDoctors() {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTORS_CSV))) {
            String line;
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
                    Doctor doctor = new Doctor(hospitalID, password, name, email, phone, dob, gender, specialization, null, null);
                    staffList.add(doctor);
                }
            }
            System.out.println("Doctors loaded successfully from CSV.");
        } catch (IOException e) {
            System.out.println("Error loading doctors from CSV: " + e.getMessage());
        }
    }

    // Load administrators from CSV
    private void loadAdministrators() {
        try (BufferedReader br = new BufferedReader(new FileReader(ADMINISTRATORS_CSV))) {
            String line;
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
                    Administrator administrator = new Administrator(hospitalID, password, name, email, phone, dob, gender, null, null, null);
                    staffList.add(administrator);
                }
            }
            System.out.println("Administrators loaded successfully from CSV.");
        } catch (IOException e) {
            System.out.println("Error loading administrators from CSV: " + e.getMessage());
        }
    }

    // Load pharmacists from CSV
    private void loadPharmacists() {
        try (BufferedReader br = new BufferedReader(new FileReader(PHARMACISTS_CSV))) {
            String line;
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
                    Pharmacist pharmacist = new Pharmacist(hospitalID, password, name, email, phone, dob, gender, null, null);
                    staffList.add(pharmacist);
                }
            }
            System.out.println("Pharmacists loaded successfully from CSV.");
        } catch (IOException e) {
            System.out.println("Error loading pharmacists from CSV: " + e.getMessage());
        }
    }

    // Add a new staff member and save to CSV
    public boolean addStaffMember(User staff) {
        for (User user : staffList) {
            if (user.getHospitalID().equals(staff.getHospitalID())) {
                System.out.println("Staff member with this Hospital ID already exists.");
                return false;
            }
        }
        staffList.add(staff);
        writeStaffToCSV(staff); // Write to CSV after adding
        System.out.println("Staff member added successfully.");
        return true;
    }

    // Update an existing staff member's details and overwrite CSV
    public boolean updateStaffMember(String hospitalID, User updatedStaff) {
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getHospitalID().equals(hospitalID)) {
                staffList.set(i, updatedStaff);
                System.out.println("Staff member updated successfully for ID: " + hospitalID);
                overwriteCSVFile(); // Overwrite CSV after updating
                return true;
            }
        }
        System.out.println("Staff member not found for ID: " + hospitalID);
        return false;
    }

    // Remove a staff member by hospital ID and overwrite CSV
    public boolean removeStaffMember(String hospitalID) {
        boolean removed = staffList.removeIf(staff -> staff.getHospitalID().equals(hospitalID));
        if (removed) {
            System.out.println("Staff member removed successfully.");
            overwriteCSVFile(); // Overwrite CSV after removing
        } else {
            System.out.println("Staff member not found.");
        }
        return removed;
    }

    // View all staff members
    public void viewAllStaff() {
        if (staffList.isEmpty()) {
            System.out.println("No staff members available.");
        } else {
            for (User staff : staffList) {
                System.out.println("Staff ID: " + staff.getHospitalID() + ", Name: " + staff.getName() + ", Role: " + staff.getClass().getSimpleName());
            }
        }
    }

    // Helper method to write a single staff member to the appropriate CSV file
    private void writeStaffToCSV(User staff) {
        String fileName;
        String line;

        if (staff instanceof Doctor) {
            fileName = DOCTORS_CSV;
            Doctor doctor = (Doctor) staff;
            line = String.join(",", doctor.getHospitalID(), doctor.getPassword(), doctor.getName(), doctor.getEmail(), doctor.getPhone(), doctor.getSpecialization());
        } else if (staff instanceof Pharmacist) {
            fileName = PHARMACISTS_CSV;
            line = String.join(",", staff.getHospitalID(), staff.getPassword(), staff.getName(), staff.getEmail(), staff.getPhone());
        } else if (staff instanceof Administrator) {
            fileName = ADMINISTRATORS_CSV;
            line = String.join(",", staff.getHospitalID(), staff.getPassword(), staff.getName(), staff.getEmail(), staff.getPhone());
        } else {
            System.out.println("Unknown role. Cannot save to CSV.");
            return;
        }

        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(line);
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // Helper method to overwrite CSV files with current staff list
    private void overwriteCSVFile() {
        try {
            // Clear and overwrite each CSV file based on current staff list
            new PrintWriter(new FileWriter(DOCTORS_CSV)).close();
            new PrintWriter(new FileWriter(ADMINISTRATORS_CSV)).close();
            new PrintWriter(new FileWriter(PHARMACISTS_CSV)).close();

            for (User staff : staffList) {
                writeStaffToCSV(staff);
            }
        } catch (IOException e) {
            System.out.println("Error overwriting CSV files: " + e.getMessage());
        }
    }

    // Get a staff member by ID
    public User getStaffByID(String hospitalID) {
        for (User staff : staffList) {
            if (staff.getHospitalID().equals(hospitalID)) {
                return staff;
            }
        }
        System.out.println("Staff member not found with ID: " + hospitalID);
        return null;
    }

    public void manageStaff(String action, User staff) {
        switch (action.toLowerCase()) {
            case "add" -> addStaffMember(staff);

            case "update" -> {
                String hospitalID = staff.getHospitalID();
                updateStaffMember(hospitalID, staff);
            }

            case "remove" -> {
                String hospitalID = staff.getHospitalID();
                if (removeStaffMember(hospitalID)) {
                    System.out.println("Staff member removed successfully.");
                } else {
                    System.out.println("Staff member not found.");
                }
            }

            case "view" -> viewAllStaff();

            default -> System.out.println("Invalid action. Please choose 'add', 'update', 'remove', or 'view'.");
        }
    }
}
