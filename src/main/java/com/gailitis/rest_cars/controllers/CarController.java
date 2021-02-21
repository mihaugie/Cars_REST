package com.gailitis.rest_cars.controllers;

import com.gailitis.rest_cars.model.Car;
import com.gailitis.rest_cars.services.CarService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/cars")
@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public List<Car> allCars() throws IOException {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public Car carById(@PathVariable int id) throws IOException {
        return carService.getCarById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Car add(@RequestBody Car car) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
       return carService.addCar(car);
    }

    @DeleteMapping("/remove/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean remove(@PathVariable int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, InterruptedException {
        return carService.removeCarById(id);
    }

    @PutMapping("/update/{id}")
    public Car update(@PathVariable int id, @RequestBody Car car) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, InterruptedException {
        return carService.updateCar(id, car);
    }

    @PostMapping("/upload/{color}")
    public void appendData(@PathVariable String color) throws IOException, InterruptedException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        carService.appendData(color);
    }

}
