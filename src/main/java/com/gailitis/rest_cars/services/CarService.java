package com.gailitis.rest_cars.services;

import com.gailitis.rest_cars.csv_utils.CSVReaderTool2;
import com.gailitis.rest_cars.dto.Cars;
import com.gailitis.rest_cars.model.Car;
import com.google.common.collect.Iterables;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Data
public class CarService {

    private final CSVReaderTool2 csvReaderTool;
    private final Cars cars;

    public List<Car> getAllCars() {
        return cars.getCarList();
    }

    public Optional<Car> getCarById(int id) {
        return cars.getCarList().stream()
                .filter(car -> car.getId() == id)
                .findFirst();
    }

    public Car addCar(Car car) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        car.setId(Iterables.getLast(cars.getCarList()).getId() + 1);
        return cars.addCar(car);
    }

    public boolean removeCarById(int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, InterruptedException {
        Optional<Car> carWithId = cars.getCarList().stream()
                .filter(car -> car.getId() == id)
                .findFirst();

        return cars.removeCarById(carWithId.get());
    }

    public Car updateCar(int id, Car providedCar) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, InterruptedException {
        Car carToBeUpdated = cars.getCarList().stream()
                .filter(car -> car.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no car with provided id"));

        carToBeUpdated.setBrand(providedCar.getBrand());
        carToBeUpdated.setPurchaseDate(providedCar.getPurchaseDate());
        carToBeUpdated.setColor(providedCar.getColor());

        return cars.updateCar(cars.getCarList().indexOf(carToBeUpdated), carToBeUpdated);
    }

    public void appendData(String color) throws IOException, InterruptedException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        csvReaderTool.uploadData(color);
    }

}
