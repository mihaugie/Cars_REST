package com.gailitis.rest_cars.dao;

import com.gailitis.rest_cars.csv_utils.CSVReaderWriter;
import com.gailitis.rest_cars.csv_utils.CSVUploader;
import com.gailitis.rest_cars.model.Car;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class CarsDAO {
    private CSVReaderWriter csvReaderWriter = new CSVReaderWriter();
    private CSVUploader csvUploadReader = new CSVUploader();

    public List<Car> getCarList() throws IOException {
        CSVReaderWriter csvReaderWriter = new CSVReaderWriter();
        return csvReaderWriter.readCsv();
    }

    public Car getCarById(int providedId) throws IOException {
        List<Car> cars = csvReaderWriter.readCsv();
        Car carWithId = cars.stream().filter(car -> car.getId() == providedId).findFirst().orElseThrow(() -> new IOException("There is no car with such id"));
        return carWithId;
    }

    public Car editCar(int id, Car editedCar) throws IOException {
        List<Car> cars = csvReaderWriter.readCsv();

        for (Car car : cars
        ) {
            if (car.getId() == id) {
                car.setBrand(editedCar.getBrand());
                car.setPurchaseDate(editedCar.getPurchaseDate());
                car.setColor(editedCar.getColor());
            }
        }
        csvReaderWriter.writeCSV(cars);
        return editedCar;
    }

    public boolean deleteCar(int idOfCarToDelete) throws IOException {
        List<Car> cars = csvReaderWriter.readCsv();
        Car carToDelete = cars.stream()
                .filter(car -> car.getId() == idOfCarToDelete)
                .findFirst().orElseThrow(() -> new NoSuchElementException("There is no such car in the system"));
        cars.remove(carToDelete);
        return csvReaderWriter.writeCSV(cars);
    }

    public Car addCar(Car newCar) throws IOException {
        List<Car> cars = csvReaderWriter.readCsv();
        newCar.setId(cars.stream().reduce((first, last) -> last).get().getId() + 1);
        cars.add(newCar);
        csvReaderWriter.writeCSV(cars);
        return newCar;
    }

    public void addCarsWithColor(String color, String uploadedFilePath) throws IOException {
        List<Car> cars = csvUploadReader.readCsv(uploadedFilePath);
        List<Car> filteredCars = cars.stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());
        List<Car> carList = getCarList();
        int idOfLastCar = carList.stream().reduce((first, last) -> last).get().getId();
        for (int i = 0; i < filteredCars.size(); i++) {
            filteredCars.get(i).setId(idOfLastCar + 1 + i);
        }
        carList.addAll(filteredCars);
        csvReaderWriter.writeCSV(carList);
    }
}
