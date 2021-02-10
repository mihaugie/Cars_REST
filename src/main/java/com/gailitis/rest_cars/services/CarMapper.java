package com.gailitis.rest_cars.services;

import com.gailitis.rest_cars.dto.CarFromCSV;
import com.gailitis.rest_cars.model.Car;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Data
public class CarMapper {

    public Car fromCarFromCSVToCar(final CarFromCSV carFromCSV) {
        Car car = new Car(Integer.parseInt(carFromCSV.getId()), carFromCSV.getBrand(), carFromCSV.getPurchaseDate(), carFromCSV.getColor());

//        final Car car = Car.builder()
//                .id(Integer.getInteger(carFromCSV.getId()))
//                .brand(carFromCSV.getBrand())
//                .purchaseDate(carFromCSV.getPurchaseDate())
//                .color(carFromCSV.getColor())
//                .build();
        return car;
    }

}
