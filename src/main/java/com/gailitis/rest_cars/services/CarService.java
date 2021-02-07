package com.gailitis.rest_cars.services;

import com.gailitis.rest_cars.dto.Cars;
import com.gailitis.rest_cars.model.Car;
import com.google.common.collect.Iterables;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class CarService {

    public List<Car> getAllCars() {
        return Cars.getInstance().getCarList();
    }

    public Optional<Car> getCarById(int id) {
        return Cars.getInstance().getCarList().stream()
                .filter(car -> car.getId() == id)
                .findFirst();
    }

    public Car addCar(Car car) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        car.setId(Iterables.getLast(Cars.getInstance().getCarList()).getId() + 1);
        return Cars.getInstance().addCar(car);
    }

    public boolean removeCarById(int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, InterruptedException {
        Optional<Car> carWithId = Cars.getInstance().getCarList().stream()
                .filter(car -> car.getId() == id)
                .findFirst();

        return Cars.getInstance().removeCarById(carWithId.get());
    }

    public Car updateCar(int id, Car providedCar) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, InterruptedException {
        Car carToBeUpdated = Cars.getInstance().getCarList().stream()
                .filter(car -> car.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no car with provided id"));

        carToBeUpdated.setBrand(providedCar.getBrand());
        carToBeUpdated.setPurchaseDate(providedCar.getPurchaseDate());
        carToBeUpdated.setColor(providedCar.getColor());

        return Cars.getInstance().updateCar(Cars.getInstance().getCarList().indexOf(carToBeUpdated), carToBeUpdated);
    }


}
