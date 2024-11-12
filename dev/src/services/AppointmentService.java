package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Appointment;

public class AppointmentService {
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, List<Appointment>> doctorSchedules; // Stores appointments by doctorID
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, List<Appointment>> patientAppointments; // Stores appointments by patientID

    public AppointmentService() {
        doctorSchedules = new HashMap<>();
        patientAppointments = new HashMap<>();
    }

    // View all scheduled appointments across all doctors
    public void viewAllAppointments() {
        boolean hasAppointments = false;
        for (Map.Entry<String, List<Appointment>> entry : doctorSchedules.entrySet()) {
            String doctorID = entry.getKey();
            List<Appointment> appointments = entry.getValue();
            for (Appointment appointment : appointments) {
                if (appointment.getStatus().equals("Scheduled")) {
                    System.out.println("Doctor ID: " + doctorID +
                                       ", Patient ID: " + appointment.getPatientID() +
                                       ", Date: " + appointment.getDate() +
                                       ", Time: " + appointment.getTime() +
                                       ", Status: " + appointment.getStatus());
                    hasAppointments = true;
                }
            }
        }
        if (!hasAppointments) {
            System.out.println("No scheduled appointments found.");
        }
    }

    // View available appointment slots for a specific doctor (for demo purposes)
    public void viewAvailableAppointments(String doctorID) {
        if (doctorSchedules.containsKey(doctorID)) {
            for (Appointment appointment : doctorSchedules.get(doctorID)) {
                if (appointment.getStatus().equals("Available")) {
                    System.out.println("Date: " + appointment.getDate() + " Time: " + appointment.getTime());
                }
            }
        }
    }

    // Schedule an appointment for a patient with a doctor
    public void scheduleAppointment(String patientID, String doctorID, String date, String time) {
        Appointment newAppointment = new Appointment(doctorID, patientID, date, time, "Scheduled");
        
        // Add appointment to doctor's schedule
        doctorSchedules.computeIfAbsent(doctorID, k -> new ArrayList<>()).add(newAppointment);
        
        // Add appointment to patient's list
        patientAppointments.computeIfAbsent(patientID, k -> new ArrayList<>()).add(newAppointment);
        
        System.out.println("Appointment scheduled successfully!");
    }

    // Reschedule an appointment
    public void rescheduleAppointment(String appointmentID, String newDate, String newTime) {
        // Locate the appointment by its ID and update the date and time
        for (List<Appointment> appointments : doctorSchedules.values()) {
            for (Appointment appointment : appointments) {
                if (appointment.getAppointmentID().equals(appointmentID) && appointment.getStatus().equals("Scheduled")) {
                    appointment.setDate(newDate);
                    appointment.setTime(newTime);
                    System.out.println("Appointment rescheduled successfully!");
                    return;
                }
            }
        }
        System.out.println("Appointment not found or cannot be rescheduled.");
    }

    // Cancel an appointment
    public void cancelAppointment(String appointmentID) {
        // Locate and cancel the appointment by its ID
        for (List<Appointment> appointments : doctorSchedules.values()) {
            for (Appointment appointment : appointments) {
                if (appointment.getAppointmentID().equals(appointmentID) && appointment.getStatus().equals("Scheduled")) {
                    appointment.setStatus("Canceled");
                    System.out.println("Appointment canceled successfully!");
                    return;
                }
            }
        }
        System.out.println("Appointment not found or already canceled.");
    }

    // Set doctor availability
    public void setDoctorAvailability(String doctorID, String date, String time) {
        Appointment availableSlot = new Appointment(doctorID, null, date, time, "Available");
        doctorSchedules.computeIfAbsent(doctorID, k -> new ArrayList<>()).add(availableSlot);
        System.out.println("Doctor availability set successfully.");
    }

    // View upcoming appointments for a doctor
    public void viewDoctorAppointments(String doctorID) {
        if (doctorSchedules.containsKey(doctorID)) {
            for (Appointment appointment : doctorSchedules.get(doctorID)) {
                if (appointment.getStatus().equals("Scheduled")) {
                    System.out.println("Patient ID: " + appointment.getPatientID() + " Date: " + appointment.getDate() + " Time: " + appointment.getTime());
                }
            }
        } else {
            System.out.println("No appointments found for doctor ID: " + doctorID);
        }
    }

    // View scheduled appointments for a patient
    public void viewPatientAppointments(String patientID) {
        if (patientAppointments.containsKey(patientID)) {
            for (Appointment appointment : patientAppointments.get(patientID)) {
                System.out.println("Doctor ID: " + appointment.getDoctorID() + " Date: " + appointment.getDate() + " Time: " + appointment.getTime() + " Status: " + appointment.getStatus());
            }
        } else {
            System.out.println("No appointments found for patient ID: " + patientID);
        }
    }

    // Accept an appointment request
public void acceptAppointment(String appointmentID) {
    for (List<Appointment> appointments : doctorSchedules.values()) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID) && appointment.getStatus().equals("Requested")) {
                appointment.setStatus("Accepted");
                System.out.println("Appointment accepted successfully!");
                return;
            }
        }
    }
    System.out.println("Appointment not found or already accepted.");
}

// Decline an appointment request
public void declineAppointment(String appointmentID) {
    for (List<Appointment> appointments : doctorSchedules.values()) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID) && appointment.getStatus().equals("Requested")) {
                appointment.setStatus("Declined");
                System.out.println("Appointment declined successfully!");
                return;
            }
        }
    }
    System.out.println("Appointment not found or already declined.");
}
}


