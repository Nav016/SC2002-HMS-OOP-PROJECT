package services;

import models.MedicalRecord;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class MedicalRecordService {
    private Map<String, MedicalRecord> medicalRecords; // Stores medical records by patientID

    public MedicalRecordService() {
        medicalRecords = new HashMap<>();
    }

    // View medical records for a specific patient
    public void viewMedicalRecords(String patientID) {
        if (medicalRecords.containsKey(patientID)) {
            MedicalRecord record = medicalRecords.get(patientID);
            System.out.println("Medical Record for Patient ID: " + patientID);
            System.out.println("Diagnoses: " + record.getDiagnoses());
            System.out.println("Treatments: " + record.getTreatments());
            System.out.println("Past Appointments: " + record.getAppointmentOutcomes());
        } else {
            System.out.println("No medical record found for patient ID: " + patientID);
        }
    }

    // Update medical record with new diagnosis and treatment
    public void updateMedicalRecord(String patientID, String diagnosis, String treatment) {
        MedicalRecord record = medicalRecords.computeIfAbsent(patientID, k -> new MedicalRecord(patientID));
        record.addDiagnosis(diagnosis);
        record.addTreatment(treatment);
        System.out.println("Medical record updated successfully for patient ID: " + patientID);
    }

    // Record the outcome of a completed appointment
    public void recordAppointmentOutcome(String appointmentID, String patientID, String serviceType, String notes, String medication) {
        if (medicalRecords.containsKey(patientID)) {
            MedicalRecord record = medicalRecords.get(patientID);
            record.addAppointmentOutcome(appointmentID, serviceType, notes, medication);
            System.out.println("Appointment outcome recorded successfully for appointment ID: " + appointmentID);
        } else {
            System.out.println("No medical record found for patient ID: " + patientID);
        }
    }

    // View past appointments for a patient
    public void viewPastAppointments(String patientID) {
        if (medicalRecords.containsKey(patientID)) {
            MedicalRecord record = medicalRecords.get(patientID);
            System.out.println("Past Appointment Outcomes for Patient ID: " + patientID);
            for (String outcome : record.getAppointmentOutcomes()) {
                System.out.println(outcome);
            }
        } else {
            System.out.println("No medical record found for patient ID: " + patientID);
        }
    }
}
