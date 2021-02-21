package com.gailitis.rest_cars.services;

import com.gailitis.rest_cars.csv_utils.CSVReaderTool2;
import com.gailitis.rest_cars.dto.Cars;
import com.gailitis.rest_cars.dto.CarsDAO;
import com.gailitis.rest_cars.dto.Carss;
import com.gailitis.rest_cars.model.Car;
import com.google.common.collect.Iterables;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
@Data
public class CarService {

    private final CSVReaderTool2 csvReaderTool;
    private final Cars cars;
    private final Carss carss;
    private final CarsDAO carsDAO;

    public List<Car> getAllCars() throws IOException {
        return carsDAO.getCarList();
    }

    //    public Optional<Car> getCarById(int id) {
//        return cars.getCarList().stream()
//                .filter(car -> car.getId() == id)
//                .findFirst();
//    }
    public Car getCarById(int id) throws IOException {
        return carss.getCarById(id);
    }

    public Car addCar(Car car) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        car.setId(Iterables.getLast(cars.getCarList()).getId() + 1);
        return carss.addCar(car);
    }

    public boolean removeCarById(int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, InterruptedException {
        Car carWithId = cars.getCarList().stream()
                .filter(car -> car.getId() == id)
                .findFirst().orElseThrow(() -> new NoSuchElementException("There is no such car in the system"));

        return carss.deleteCar(id);
//        return cars.removeCarById(carWithId);
    }

    public Car updateCar(int id, Car providedCar) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, InterruptedException {
        Car carToBeUpdated = cars.getCarList().stream()
                .filter(car -> car.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no car with provided id"));

        carToBeUpdated.setBrand(providedCar.getBrand());
        carToBeUpdated.setPurchaseDate(providedCar.getPurchaseDate());
        carToBeUpdated.setColor(providedCar.getColor());

//        return cars.updateCar(cars.getCarList().indexOf(carToBeUpdated), carToBeUpdated);
        return carss.editCar(carToBeUpdated);
    }

    public void appendData(String color) throws IOException, InterruptedException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        csvReaderTool.uploadData(color);
    }

}
