package models;

import services.AppointmentService;
import services.MedicalRecordService;


public class Patient extends User {
    private String patientID;
    private String bloodType;
    private MedicalRecordService medicalRecordService;
    private AppointmentService appointmentService;

    // Constructor
    public Patient(String hospitalID, String password, String name, String email, String phone, 
                   String patientID, String bloodType, String gender, String dob, MedicalRecordService medicalRecordService, AppointmentService appointmentService) {
        super(hospitalID, password, name, email, phone, dob, gender);
        this.patientID = patientID;
        this.bloodType = bloodType;
        this.medicalRecordService = medicalRecordService;
        this.appointmentService = appointmentService;
    }

    // Role-specific methods
    public void viewMedicalRecord() {
        medicalRecordService.viewMedicalRecords(patientID);     
        
    }

    public void createRecord(){
        medicalRecordService.createMedicalRecord(patientID, name, dob, gender, phone, email, bloodType);
    }

    public void updatePersonalInfo(String email, String phone) {
        this.setEmail(email);
        this.setPhone(phone);
    }

    public void viewAvailableAppointments() {
        appointmentService.viewAllAppointments();
    }

    public void scheduleAppointment(String doctorID, String date, String time) {
        appointmentService.scheduleAppointment(patientID, doctorID, date, time);
    }

    public void rescheduleAppointment(String appointmentID, String newDate, String newTime) {
        appointmentService.rescheduleAppointment(appointmentID, newDate, newTime);
    }

    public void cancelAppointment(String appointmentID) {
        appointmentService.cancelAppointment(appointmentID);
    }

    public void viewScheduledAppointments() {
        appointmentService.viewPatientAppointments(patientID);
    }

    public void viewPastAppointmentOutcomes() {
        medicalRecordService.viewPastAppointments(patientID);
    }

    @Override
    public void showMenu() {
        System.out.println("Patient Menu:");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcomes");
        System.out.println("9. Logout");
    }
}
