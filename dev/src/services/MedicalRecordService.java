package services;

import models.MedicalRecord;
import java.util.HashMap;
import java.util.Map;


public class MedicalRecordService {
    private Map<String, MedicalRecord> medicalRecords; // Stores medical records by patientID

    public MedicalRecordService() {
        medicalRecords = new HashMap<>();
    }

    // View medical records for a specific patient
    public void viewMedicalRecords(String patientID) {
        MedicalRecord record = medicalRecords.get(patientID);
        System.out.println("Medical Record for Patient ID: " + patientID);
        System.out.println("Name: " + record.getName());
        System.out.println("Date of Birth: " + record.getDob());
        System.out.println("Gender: " + record.getGender());
        System.out.println("Contact Number: " + record.getContactNo());
        System.out.println("Email Address: " + record.getEmail());
        System.out.println("Blood Type: " + record.getBloodType());
        System.out.println("Diagnoses: " + record.getDiagnoses());
        System.out.println("Treatments: " + record.getTreatments());
        System.out.println("Past Appointments: " + record.getAppointmentOutcomes());      
    }

    //check if there already exist medical record
    public boolean checkIfExistMedicalRecord(String patientID){
        if (medicalRecords.containsKey(patientID)){
            return true;
        }
        else{
            return false;
        }
    }

    //create medical record
    public void createMedicalRecord(String patientID, String name, String dob, String gender, String contactNo, String email, String bloodType){
        MedicalRecord record = new MedicalRecord(patientID, name, dob, gender, contactNo, email, bloodType);
        medicalRecords.put(patientID, record);
        System.out.println("Medical Record created successfully. ");
    }

    //update phone and email
    public void updatePhoneEmail(String patientID, String contactNo, String email){
        MedicalRecord record = medicalRecords.get(patientID);
        if (record != null) {
            System.out.println("Old Email: " + record.getEmail());
            System.out.println("Old Contact: " + record.getContactNo());
            record.setContactNo(contactNo);
            record.setEmail(email);
            System.out.println("Phone and Email successfully updated");
        }
        else{
            System.out.println("Medical Record not found");
        }

    }
    // Update medical record with new diagnosis and treatment
    public void updateMedicalRecord(String patientID, String diagnosis, String treatment) {
        //MedicalRecord record = medicalRecords.computeIfAbsent(patientID, k -> new MedicalRecord(patientID));
        MedicalRecord record = medicalRecords.get(patientID);
        if (record != null){
            record.addDiagnosis(diagnosis);
            record.addTreatment(treatment);
            System.out.println("Medical record updated successfully for patient ID: " + patientID);
        }
        else{
            System.out.println("No medical record found for patient ID " + patientID);
        }
        
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
