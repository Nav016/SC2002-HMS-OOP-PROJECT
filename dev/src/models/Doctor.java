package models;

import services.AppointmentService;
import services.MedicalRecordService;

public class Doctor extends User {
    private String specialization;
    private AppointmentService appointmentService;
    private MedicalRecordService medicalRecordService;

    // Constructor
    public Doctor(String hospitalID, String password, String name, String email, String phone,
                  String specialization, AppointmentService appointmentService, MedicalRecordService medicalRecordService) {
        super(hospitalID, password, name, email, phone);
        this.specialization = specialization;
        this.appointmentService = appointmentService;
        this.medicalRecordService = medicalRecordService;
    }

    public String getSpecialization() {
        return specialization;
    }

    // Role-specific methods
    public void viewPatientMedicalRecords(String patientID) {
        medicalRecordService.viewMedicalRecords(patientID);
    }

    public void updatePatientMedicalRecords(String patientID, String diagnosis, String treatment) {
        medicalRecordService.updateMedicalRecord(patientID, diagnosis, treatment);
    }

    public void setAvailability(String date, String time) {
        appointmentService.setDoctorAvailability(this.getHospitalID(), date, time);
    }

    public void acceptAppointmentRequest(String appointmentID) {
        appointmentService.acceptAppointment(appointmentID);
    }

    public void declineAppointmentRequest(String appointmentID) {
        appointmentService.declineAppointment(appointmentID);
    }

    public void viewUpcomingAppointments() {
        appointmentService.viewDoctorAppointments(this.getHospitalID());
    }

    public void recordAppointmentOutcome(String appointmentID, String patientID, String serviceType, String notes, String medication) {
        medicalRecordService.recordAppointmentOutcome(appointmentID, patientID, serviceType, notes, medication);
    }

    @Override
    public void showMenu() {
        System.out.println("Doctor Menu:");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. Set Availability for Appointments");
        System.out.println("4. Accept or Decline Appointment Requests");
        System.out.println("5. View Upcoming Appointments");
        System.out.println("6. Record Appointment Outcome");
        System.out.println("7. Logout");
    }
}
