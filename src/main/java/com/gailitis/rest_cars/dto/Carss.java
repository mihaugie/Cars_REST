package com.gailitis.rest_cars.dto;

import com.gailitis.rest_cars.csv_utils.CSVReaderApache;
import com.gailitis.rest_cars.model.Car;
import com.gailitis.rest_cars.services.CarMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
//@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carss {

    private List<Car> carList;
    private CarMapper carMapper;
    private CSVReaderApache csvReaderApache = new CSVReaderApache();

    public List<Car> getCarList() throws IOException {
        return csvReaderApache.readCsv();
    }
    public Car getCarById(int id) throws IOException {
        return csvReaderApache.getCarById(id);
    }

    public Car editCar(Car car) throws IOException {
        return csvReaderApache.editCar(car, this);
    }

    public boolean deleteCar(int id) throws IOException {
        return csvReaderApache.deleteCar(id, this);
    }

    public Car addCar(Car newCar) throws IOException {
        return csvReaderApache.addCar(newCar, this);
    }


}
