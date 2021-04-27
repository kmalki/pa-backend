package com.esgi.flexges.model;

public class UserApp {

    private String email;
    private String password;
    private String enterprise;
    private boolean isAdmin;

    public UserApp() {
    }

    public UserApp(String email, String enterprise) {
        this.email = email;
        this.enterprise = enterprise;
    }

    public UserApp(String email, String password, String enterprise, boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.enterprise = enterprise;
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "UserApp{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enterprise='" + enterprise + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}