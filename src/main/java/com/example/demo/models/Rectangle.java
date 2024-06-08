package com.example.demo.models;

import jakarta.persistence.*;   // * means for all

@Entity
@Table(name="rectangle")
public class Rectangle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Purpose of the above 2 lines: create a SERIAL data type for the variable below which is "id"

    private int id;
    private String name;
    private int width;
    private int height;
    private String color;
    // This table has 4 attributes and 1 of them is "id" that is gonna auto increment

    public Rectangle() {
    }
    public Rectangle(String name, int width, int height, String color) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
