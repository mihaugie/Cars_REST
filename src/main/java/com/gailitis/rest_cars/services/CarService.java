package com.gailitis.rest_cars.services;

import com.gailitis.rest_cars.csv_utils.CSVMappedToCars;
import com.gailitis.rest_cars.dto.Cars;
import com.gailitis.rest_cars.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CarService {
    private final CSVMappedToCars csvCars;

    private final Cars cars;

    public List<Car> getAllCars() {
//        for (CarFromCSV csvCar: csvCars
//             ) {
//
//        }
        return Cars.getInstance().getCarList();
    }


}
