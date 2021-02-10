package com.gailitis.rest_cars.csv_utils;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.gailitis.rest_cars.model.Car;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
@Transactional
@Data
public class CsvUpdater {
    String csvFilePath = CsvConsts.DATA_FILE_PATH;

    public void addNewCarToCsv(Car car) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath, true));
        String carToCsv = car.getId() + "," + car.getBrand() + "," + car.getPurchaseDate() + "," + car.getColor();
        String [] record = carToCsv.split(",");
        writer.writeNext(record);
        writer.close();
    }

    public void updateCsvFile(Car car) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath));
        String carToCsv = car.getId() + "," + car.getBrand() + "," + car.getPurchaseDate() + "," + car.getColor();
        String [] record = carToCsv.split(",");
        writer.writeNext(record);
        writer.close();
    }

    public void removeCarFromCsv(Car car) throws IOException {
        CSVReader reader2 = new CSVReader(new FileReader(csvFilePath));
        List<String[]> allElements = reader2.readAll();
        allElements.remove(2);
        FileWriter sw = new FileWriter(csvFilePath);
        CSVWriter writer = new CSVWriter(sw);
        writer.writeAll(allElements);
        writer.close();
    }
}
