package com.gailitis.rest_cars.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String id;
    private String firstName;
    private String lastName;
    private String country;
    private String age;

    //Getters and setters

    @Override
    public String toString()
    {
        return "Employee [id=" + id + ", firstName=" + firstName + ",lastName=" + lastName + ", country=" + country + ", age=" + age + "]";
    }
}
