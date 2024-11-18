package models;

public abstract class User {

    public String hospitalID;
    private String password;
    public String name;
    public String email;
    public String phone;
    public String dob;
    public String gender;

    // Constructor
    public User(String hospitalID, String password, String name, String email, String phone, String dob, String gender) {
        this.hospitalID = hospitalID;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
    }

    // Getters and Setters
    public String getHospitalID() {
        return hospitalID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getdob(){
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Abstract method for role-specific menu
    public abstract void showMenu();
}
