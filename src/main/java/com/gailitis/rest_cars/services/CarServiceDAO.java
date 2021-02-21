package com.gailitis.rest_cars.services;

import com.gailitis.rest_cars.dto.CarsDAO;
import com.gailitis.rest_cars.model.Car;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Transactional
@Service
@Data
public class CarServiceDAO {

    private final CarsDAO carsDAO;

    public List<Car> getAllCars() throws IOException {
        return carsDAO.getCarList();
    }

    public Car getCarById(int id) throws IOException {
        return carsDAO.getCarById(id);
    }

    public Car addCar(Car car) throws IOException {
//        car.setId(Iterables.getLast(cars.getCarList()).getId() + 1);
        return carsDAO.addCar(car);
    }

    public boolean removeCarById(int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, InterruptedException {
        return carsDAO.deleteCar(id);
//        return cars.removeCarById(carWithId);
    }

    public Car updateCar(int id, Car providedCar) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, InterruptedException {
//        Car carToBeUpdated = cars.getCarList().stream()
//                .filter(car -> car.getId() == id)
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("There is no car with provided id"));
//
//        carToBeUpdated.setBrand(providedCar.getBrand());
//        carToBeUpdated.setPurchaseDate(providedCar.getPurchaseDate());
//        carToBeUpdated.setColor(providedCar.getColor());

//        return cars.updateCar(cars.getCarList().indexOf(carToBeUpdated), carToBeUpdated);
        return carsDAO.editCar(id, providedCar);
    }

//    public void appendData(String color) throws IOException, InterruptedException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        csvReaderTool.uploadData(color);
//    }

}
