package com.gailitis.rest_cars.dto;

import com.gailitis.rest_cars.csv_utils.CSVReaderApacheDAO;
import com.gailitis.rest_cars.model.Car;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class CarsDAO {
    private CSVReaderApacheDAO csvReaderApacheDAO = new CSVReaderApacheDAO();

    public List<Car> getCarList() throws IOException {
        CSVReaderApacheDAO csvReaderApacheDAO = new CSVReaderApacheDAO();
        return csvReaderApacheDAO.readCsv();
    }

    public Car getCarById(int providedId) throws IOException {
        List<Car> cars = csvReaderApacheDAO.readCsv();
        Car carWithId = cars.stream().filter(car -> car.getId() == providedId).findFirst().orElseThrow(() -> new IOException("There is no car with such id"));
        return carWithId;
    }

    public Car editCar(int id, Car editedCar) throws IOException {
        List<Car> cars = csvReaderApacheDAO.readCsv();

        for (Car car : cars
        ) {
            if (car.getId() == id) {
                car.setBrand(editedCar.getBrand());
                car.setPurchaseDate(editedCar.getPurchaseDate());
                car.setColor(editedCar.getColor());
            }
        }
        csvReaderApacheDAO.writeCSV(cars);
        return editedCar;
    }

    public boolean deleteCar(int idOfCarToDelete) throws IOException {
        List<Car> cars = csvReaderApacheDAO.readCsv();
        Car carToDelete = cars.stream()
                .filter(car -> car.getId() == idOfCarToDelete)
                .findFirst().orElseThrow(() -> new NoSuchElementException("There is no such car in the system"));
        cars.remove(carToDelete);
        return csvReaderApacheDAO.writeCSV(cars);
    }

    public Car addCar(Car newCar) throws IOException {
        List<Car> cars = csvReaderApacheDAO.readCsv();
        newCar.setId(cars.stream().reduce((first, last) -> last).get().getId() + 1);
        cars.add(newCar);
        csvReaderApacheDAO.writeCSV(cars);
        return newCar;
    }
}
