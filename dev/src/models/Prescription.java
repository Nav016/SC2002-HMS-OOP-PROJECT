package models;

import java.util.UUID;

public class Prescription {
    private String prescriptionID;
    private String patientID;
    private String doctorID;
    private String medication;
    private String status; // e.g., "Pending", "Dispensed"

    // Constructor
    public Prescription(String patientID, String doctorID, String medication, String status) {
        this.prescriptionID = UUID.randomUUID().toString(); // Generate a unique ID for each prescription
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.medication = medication;
        this.status = status;
    }

    // Getters and Setters
    public String getPrescriptionID() {
        return prescriptionID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Prescription ID: " + prescriptionID +
               ", Patient ID: " + patientID +
               ", Doctor ID: " + doctorID +
               ", Medication: " + medication +
               ", Status: " + status;
    }
}
