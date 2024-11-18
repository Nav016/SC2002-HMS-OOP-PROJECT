package services;

import java.util.HashMap;
import java.util.Map;
import models.*;


public class AuthenticationService {
    private Map<String, User> users;

    public AuthenticationService() {
        this.users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getHospitalID(), user);
    }

    public User authenticate(String hospitalID, String password) {
        User user = users.get(hospitalID);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User createUser(String role, String hospitalID, String password, String name, String email, String phone, String dob, String gender,
                           String additionalInfo1, String additionalInfo2) {
        User newUser = null;

        switch (role.toLowerCase()) {
            case "doctor" -> {
                String specialization = additionalInfo1;
                newUser = new Doctor(hospitalID, password, name, email, phone, dob, gender, specialization, null, null);
            }
            case "patient" -> {
                String patientID = additionalInfo1;
                String bloodType = additionalInfo2;
                newUser = new Patient(hospitalID, password, name, email, phone, dob, gender, patientID, bloodType, null, null);
                
            }
            case "pharmacist" -> newUser = new Pharmacist(hospitalID, password, name, email, phone, dob, gender, null, null);
            case "administrator" -> newUser = new Administrator(hospitalID, password, name, email, phone, dob, gender, null, null, null);
            default -> {
                System.out.println("Invalid role specified.");
                return null;
            }
        }

        addUser(newUser);
        return newUser;
    }

    public boolean userExists(String hospitalID) {
        return users.containsKey(hospitalID);
    }
}
