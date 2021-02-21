package com.gailitis.rest_cars.controllers;

import com.gailitis.rest_cars.csv_utils.CsvConsts;
import com.gailitis.rest_cars.model.Car;
import com.gailitis.rest_cars.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Car add(@RequestBody Car car) throws IOException {
       return carService.addCar(car);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean remove(@PathVariable int id) throws IOException {
        return carService.removeCarById(id);
    }

    @PutMapping("/{id}")
    public Car update(@PathVariable int id, @RequestBody Car car) throws IOException {
        return carService.updateCar(id, car);
    }

    @PostMapping(value = "/uploadfile/{color}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String color) {
        String fileName = file.getOriginalFilename();
        try{
            String uploadedFilePath = CsvConsts.FILEPATH_TO_SAVE_CARS_WITH_COLOR_FILTER + fileName;
            file.transferTo(new File(uploadedFilePath));
            carService.uploadDataWithColorFilter(color, uploadedFilePath);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok("File uploaded succesfully");
    }
}
