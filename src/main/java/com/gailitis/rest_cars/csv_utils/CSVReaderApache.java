package com.gailitis.rest_cars.csv_utils;

import com.gailitis.rest_cars.dto.CarFromCSV;
import com.gailitis.rest_cars.dto.Carss;
import com.gailitis.rest_cars.model.Car;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@Data
public class CSVReaderApache {
    private static Scanner scanner;

    private static final String csvFilePath = CsvConsts.DATA_FILE_SAMOCHODY_APACHE;
    private static final String csvFilePathToSave = CsvConsts.DATA_FILE_SAMOCHODY_APACHE_SAVE;

    public List<Car> readCsv() throws IOException {

        List<CarFromCSV> carFromCSVList = new ArrayList<CarFromCSV>();
        List<Car> carList = new ArrayList<Car>();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath), Charset.defaultCharset());
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by Header names
                String id = csvRecord.get("Id");
                String brand = csvRecord.get("Nazwa");
                String purchaseDate = csvRecord.get("Data zakupu");
                String color = csvRecord.get("Kolor");

//                CarFromCSV carFromCSV = new CarFromCSV(csvRecord.get("Id"), csvRecord.get("Nazwa"), csvRecord.get("Data zakupu"), csvRecord.get("Kolor"));
                Car car = new Car(Integer.parseInt(id), brand, purchaseDate, color);
//                carFromCSVList.add(carFromCSV);
                carList.add(car);
            }
        }
        return carList;
    }

    public Car getCarById(int providedId) throws IOException {

        List<CarFromCSV> carFromCSVList = new ArrayList<CarFromCSV>();
        List<Car> carList = new ArrayList<Car>();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath), Charset.defaultCharset());
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by Header names
                String id = csvRecord.get("Id");
                String brand = csvRecord.get("Nazwa");
                String purchaseDate = csvRecord.get("Data zakupu");
                String color = csvRecord.get("Kolor");

                Car car = new Car(Integer.parseInt(id), brand, purchaseDate, color);
                carList.add(car);
            }
        }
        Car carWithId = carList.get(carList.stream().filter(car -> car.getId() == providedId).findFirst().get().getId());
        return carWithId;
    }


    public Car editCar(Car editedCar, Carss carss) throws IOException {
        List<Car> carList = carss.getCarList();
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFilePathToSave), Charset.defaultCharset());

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("Id", "Nazwa", "Data zakupu", "Kolor"));
        ) {
            for (Car car : carList
            ) {

                if (car.getId() == editedCar.getId()) {
                    csvPrinter.printRecord(String.valueOf(editedCar.getId()), editedCar.getBrand(), editedCar.getPurchaseDate(), editedCar.getColor());
                } else {
                    csvPrinter.printRecord(String.valueOf(car.getId()), car.getBrand(), car.getPurchaseDate(), car.getColor());
                }
            }

            csvPrinter.flush();
        }
        return editedCar;
    }

    public Car addCar(Car newCar, Carss carss) throws IOException {
        List<Car> carList = carss.getCarList();
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFilePathToSave), Charset.defaultCharset());

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("Id", "Nazwa", "Data zakupu", "Kolor"));
        ) {

            for (Car car : carList
            ) {
                csvPrinter.printRecord(String.valueOf(car.getId()), car.getBrand(), car.getPurchaseDate(), car.getColor());
            }


            csvPrinter.printRecord(String.valueOf(carList.stream().reduce((first, last) -> last).get().getId() + 1), newCar.getBrand(), newCar.getPurchaseDate(), newCar.getColor());

            csvPrinter.flush();
        }
        return newCar;
    }


    public boolean deleteCar(int idOfCarToDelete, Carss carss) throws IOException {
        List<Car> carList = carss.getCarList();
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFilePathToSave), Charset.defaultCharset());

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("Id", "Nazwa", "Data zakupu", "Kolor"));
        ) {
            for (Car car : carList
            ) {

                if (car.getId() == idOfCarToDelete) {
                    return true;
                } else {
                    csvPrinter.printRecord(String.valueOf(car.getId()), car.getBrand(), car.getPurchaseDate(), car.getColor());
                }
            }

            csvPrinter.flush();
        }
        return false;
    }
}
