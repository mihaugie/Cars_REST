package com.gailitis.rest_cars.services;

import com.gailitis.rest_cars.csv_utils.CSVMappedToCars;
import com.gailitis.rest_cars.dto.Cars;
import com.gailitis.rest_cars.model.Car;
import com.google.common.collect.Iterables;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class CarService {
    private final CSVMappedToCars csvCars;

    private final Cars cars;

    public List<Car> getAllCars() {
        return Cars.getInstance().getCarList();
    }

    public Optional<Car> getCarById(int id) {
        return Cars.getInstance().getCarList().stream()
                .filter(car -> car.getId() == id)
                .findFirst();
//        return Cars.getInstance().getCarById(id - 1);
    }

    public Car addCar(Car car) {
        car.setId(Iterables.getLast(Cars.getInstance().getCarList()).getId() + 1);
        return Cars.getInstance().addCar(car);
    }

    public boolean removeCarById(int id) {
        Optional<Car> carWithId = Cars.getInstance().getCarList().stream()
                .filter(car -> car.getId() == id)
                .findFirst();

        return Cars.getInstance().removeCarById(carWithId.get());
    }

    public Car updateCar(int id, Car providedCar) {
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
