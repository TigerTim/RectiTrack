package com.example.demo.models;

import jakarta.persistence.*;   // * means for all

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Purpose of the above 2 lines: create a SERIAL data type for the variable below which is "uid"

    private int uid;
    private String name;
    private String password;
    private int size;
    // This table has 4 attributes and 1 of them is "uid" that is gonna auto increment

    public User() {
    }
    public User(String name, String password, int size) {
        this.name = name;
        this.password = password;
        this.size = size;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    
}
