package com.esgi.flexges.model;

public class Room {

    private String enterprise;
    private String name;
    private int capacity;
    private int current = 0;

    public Room() {
    }

    public Room(String enterprise, String name, int capacity) {
        this.enterprise = enterprise;
        this.name = name;
        this.capacity = capacity;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Room{" +
                "enterprise='" + enterprise + '\'' +
                ", name='" + name + '\'' +
                ", current=" + current +
                ", capacity=" + capacity +
                '}';
    }
}
