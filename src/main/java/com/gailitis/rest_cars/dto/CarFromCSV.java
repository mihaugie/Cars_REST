package com.gailitis.rest_cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarFromCSV implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String brand;
    private String purchaseDate;
    private String color;


    @Override
    public String toString() {
        return "Car [" +
                "id=" + id +
                ", brand='" + brand +
                ", purchaseDate=" + purchaseDate +
                ", color='" + color +
                ']';
    }
}
