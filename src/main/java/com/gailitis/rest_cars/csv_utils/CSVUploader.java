package com.gailitis.rest_cars.csv_utils;

import com.gailitis.rest_cars.model.Car;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

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
public class CSVUploader {
    private static Scanner scanner;

    private static final String csvFilePath = CsvConsts.DATA_FILE_SAMOCHODY;
    private static final String csvFilePathToSave = CsvConsts.DATA_FILE_SAMOCHODY_SAVE_PATH;

    public List<Car> readCsv(String filePath) throws IOException {

        List<Car> carList = new ArrayList<Car>();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePath), Charset.defaultCharset());
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
        return carList;
    }
}
