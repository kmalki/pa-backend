package com.esgi.flexges.model;

import java.util.List;

public class Enterprise {
    private String name;
    private String adminEmail;
    private String adminPassword;
    private String address;
    private String city;

    public Enterprise() {
    }

    public Enterprise(String name, String adminEmail, String adminPassword, String address, String city) {
        this.name = name;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.address = address;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "name='" + name + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", employees=" + employees +
                '}';
    }
}
