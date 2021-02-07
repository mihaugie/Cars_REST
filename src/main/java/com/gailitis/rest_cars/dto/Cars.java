package com.gailitis.rest_cars.dto;

import com.gailitis.rest_cars.csv_utils.CsvUpdater;
import com.gailitis.rest_cars.csv_utils.CsvWriterTool;
import com.gailitis.rest_cars.model.Car;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class Cars {
    private static Cars instance;

    private List<Car> carList = new ArrayList<>();
    private CsvUpdater csvUpdater = new CsvUpdater();
    private CsvWriterTool csvWriterTool = new CsvWriterTool();

    public static Cars getInstance() {
        if (instance == null) {
            instance = new Cars();
        }
        return instance;
    }

    public Car uploadData(Car car) {
        carList.add(car);
        return car;
    }

    public Car addCar(Car car) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        carList.add(car);
        csvWriterTool.updateCsvFile();
        return car;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public boolean removeCarById(Car car) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, InterruptedException {
        csvUpdater.removeCarFromCsv(car);
        csvWriterTool.updateCsvFile();
        return carList.remove(car);
    }

    public Car updateCar(int id, Car car) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, InterruptedException {
        Car updatedCar = carList.set(id, car);
        csvWriterTool.updateCsvFile();
        return updatedCar;

    }
}
