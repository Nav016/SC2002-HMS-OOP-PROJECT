package models;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private String patientID;
    private List<String> diagnoses;
    private List<String> treatments;
    private List<String> appointmentOutcomes;

    // Constructor
    public MedicalRecord(String patientID) {
        this.patientID = patientID;
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.appointmentOutcomes = new ArrayList<>();
    }

    // Getters and Setters
    public String getPatientID() {
        return patientID;
    }

    public List<String> getDiagnoses() {
        return diagnoses;
    }

    public List<String> getTreatments() {
        return treatments;
    }

    public List<String> getAppointmentOutcomes() {
        return appointmentOutcomes;
    }

    // Add a diagnosis
    public void addDiagnosis(String diagnosis) {
        diagnoses.add(diagnosis);
    }

    // Add a treatment
    public void addTreatment(String treatment) {
        treatments.add(treatment);
    }

    // Record an appointment outcome
    public void addAppointmentOutcome(String appointmentID, String serviceType, String notes, String medication) {
        String outcome = "Appointment ID: " + appointmentID + 
                         ", Service Type: " + serviceType +
                         ", Notes: " + notes + 
                         ", Medication: " + medication;
        appointmentOutcomes.add(outcome);
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientID +
               "\nDiagnoses: " + diagnoses +
               "\nTreatments: " + treatments +
               "\nAppointment Outcomes: " + appointmentOutcomes;
    }
}
