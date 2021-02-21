package com.gailitis.rest_cars.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Car implements Serializable {

    private int id;
    private String brand;
    private String purchaseDate;
    private String color;

    public Car(int id, String brand, String purchaseDate, String color) {
        this.id = id;
        this.brand = brand;
        this.purchaseDate = purchaseDate;
        this.color = color;
    }
}
