package com.gailitis.rest_cars.services;

import com.gailitis.rest_cars.dto.CarFromCSV;
import com.gailitis.rest_cars.model.Car;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CarMapper {

    public Car fromCarFromCSVToCar(final CarFromCSV carFromCSV) {
        Car car = new Car(Integer.parseInt(carFromCSV.getId()), carFromCSV.getBrand(), carFromCSV.getPurchaseDate(), carFromCSV.getColor());

        return car;
    }

}
