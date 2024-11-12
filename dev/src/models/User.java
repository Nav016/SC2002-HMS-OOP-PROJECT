package models;

public abstract class User {

    private String hospitalID;
    private String password;
    private String name;
    private String email;
    private String phone;

    // Constructor
    public User(String hospitalID, String password, String name, String email, String phone) {
        this.hospitalID = hospitalID;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    // Abstract method for role-specific menu
    public abstract void showMenu();
}
