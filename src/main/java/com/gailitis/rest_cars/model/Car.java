package com.gailitis.rest_cars.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@Builder
public class Car implements Serializable {
//    private static final long serialVersionUID = 1L;

    @Id
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

    @Override
    public String toString() {
        return "Car [" +
                "id=" + id +
                ", brand=" + brand +
                ", purchaseDate=" + purchaseDate +
                ", color=" + color +
                ']';
    }
}
