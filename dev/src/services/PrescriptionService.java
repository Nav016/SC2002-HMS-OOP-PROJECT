package services;

import java.util.HashMap;
import java.util.Map;
import models.Prescription;

public class PrescriptionService {
    private Map<String, Prescription> prescriptions; // Stores prescriptions by prescriptionID

    public PrescriptionService() {
        prescriptions = new HashMap<>();
    }

    // View a specific prescription
    public void viewPrescription(String prescriptionID) {
        if (prescriptions.containsKey(prescriptionID)) {
            Prescription prescription = prescriptions.get(prescriptionID);
            System.out.println("Prescription ID: " + prescriptionID);
            System.out.println("Patient ID: " + prescription.getPatientID());
            System.out.println("Doctor ID: " + prescription.getDoctorID());
            System.out.println("Medication: " + prescription.getMedication());
            System.out.println("Status: " + prescription.getStatus());
        } else {
            System.out.println("Prescription not found for ID: " + prescriptionID);
        }
    }

    // Update the status of a prescription (e.g., "Pending" to "Dispensed")
    public void updatePrescriptionStatus(String prescriptionID, String status) {
        if (prescriptions.containsKey(prescriptionID)) {
            Prescription prescription = prescriptions.get(prescriptionID);
            prescription.setStatus(status);
            System.out.println("Prescription status updated successfully to: " + status);
        } else {
            System.out.println("Prescription not found for ID: " + prescriptionID);
        }
    }

    // Create a new prescription (called by doctor when prescribing medication)
    public void createPrescription(String patientID, String doctorID, String medication, String status) {
        Prescription newPrescription = new Prescription(patientID, doctorID, medication, status);
        prescriptions.put(newPrescription.getPrescriptionID(), newPrescription);
        System.out.println("Prescription created successfully for Patient ID: " + patientID);
    }
}
