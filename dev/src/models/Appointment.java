package models;

import java.util.UUID;

public class Appointment {
    private String appointmentID;
    private String doctorID;
    private String patientID;
    private String date;
    private String time;
    private String status;
    private String serviceType; // New field for the type of service provided
    private String notes;       // New field for consultation notes
    private String medication;  // New field for prescribed medication

    // Constructor
    public Appointment(String doctorID, String patientID, String date, String time, String status) {
        this.appointmentID = UUID.randomUUID().toString(); // Generate unique ID for each appointment
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    // Getters and Setters
    public String getAppointmentID() {
        return appointmentID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }
}
