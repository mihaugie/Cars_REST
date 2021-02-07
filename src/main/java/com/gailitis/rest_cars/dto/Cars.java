package com.gailitis.rest_cars.dto;

import com.gailitis.rest_cars.dto.CarFromCSV;
import com.gailitis.rest_cars.model.Car;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class Cars {
    private static Cars instance;

    private List<Car> carList = new ArrayList<>();

    public static Cars getInstance() {
        if (instance == null) {
            instance = new Cars();
        }
        return instance;
    }

    public Car addCar(Car car) {
        carList.add(car);
        return car;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public Car getCarById(int id) {
        return carList.get(id);
    }

    public boolean removeCarById(Car car) {
        return carList.remove(car);
    }

    public Car updateCar(int id, Car car){
         return carList.set(id, car);
    }
}
