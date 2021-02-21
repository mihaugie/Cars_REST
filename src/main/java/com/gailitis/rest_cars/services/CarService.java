package com.gailitis.rest_cars.services;

import com.gailitis.rest_cars.dao.CarsDAO;
import com.gailitis.rest_cars.model.Car;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Transactional
@Service
@Data
public class CarService {

    private final CarsDAO carsDAO;

    public List<Car> getAllCars() throws IOException {
        return carsDAO.getCarList();
    }

    public Car getCarById(int id) throws IOException {
        return carsDAO.getCarById(id);
    }

    public Car addCar(Car car) throws IOException {
        return carsDAO.addCar(car);
    }

    public boolean removeCarById(int id) throws IOException {
        return carsDAO.deleteCar(id);
    }

    public Car updateCar(int id, Car providedCar) throws  IOException {
        return carsDAO.editCar(id, providedCar);
    }

    public void uploadDataWithColorFilter(String color, String uploadedFilePath) throws IOException {
        carsDAO.addCarsWithColor(color, uploadedFilePath);
    }

}
