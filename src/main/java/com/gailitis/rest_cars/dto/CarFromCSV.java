package com.gailitis.rest_cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CarFromCSV implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String brand;
    private String purchaseDate;
    private String color;

}
