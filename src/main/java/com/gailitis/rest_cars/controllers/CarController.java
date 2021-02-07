package com.gailitis.rest_cars.controllers;

import com.gailitis.rest_cars.model.Car;
import com.gailitis.rest_cars.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public List<Car> showAllCars(){
        return carService.getAllCars();
    }
}
