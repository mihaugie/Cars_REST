package com.gailitis.rest_cars.controllers;

import com.gailitis.rest_cars.model.Car;
import com.gailitis.rest_cars.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/cars")
@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public List<Car> allCars(){
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public Optional<Car> carById(@PathVariable int id){
        return carService.getCarById(id);
    }

    @PostMapping("/add")
    public Car add(@RequestBody Car car){
       return carService.addCar(car);
    }

    @DeleteMapping("/remove/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean remove(@PathVariable int id){
        return carService.removeCarById(id);
    }


    @PutMapping("/update/{id}")
    public Car update(@PathVariable int id, @RequestBody Car car){
        return carService.updateCar(id, car);
    }


}
