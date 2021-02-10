package com.gailitis.rest_cars.csv_utils;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.gailitis.rest_cars.dto.CarFromCSV;
import com.gailitis.rest_cars.dto.Cars;
import com.gailitis.rest_cars.model.Car;
import com.gailitis.rest_cars.services.CarMapper;
import com.google.common.collect.Iterables;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class CSVReaderTool2 {

    private final Cars cars;
    private final CarMapper carMapper;
    private List<CarFromCSV> carList = new ArrayList<>();
    private final CsvWriterTool csvWriterTool;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Car> uploadData(String color) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        CsvToBean csv = new CsvToBean();

        String csvFilename = CsvConsts.DATA_FILE_TO_UPLOAD_WITH_COLOR_FILTER_PATH;
        CSVReader csvReader = new CSVReader(new FileReader(csvFilename));

        carList = csv.parse(setColumnMapping(), csvReader);
        for (Object object : carList) {
            CarFromCSV carFromCSV = (CarFromCSV) object;
        }
        return revert(color);
    }

    private List<Car> revert(String color) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        for (CarFromCSV csvCar: carList
        ) {
            if (csvCar.getColor().equals(color)){
                Car car = carMapper.fromCarFromCSVToCar(csvCar);
                car.setId(Iterables.getLast(cars.getCarList()).getId() + 1);
                cars.uploadData(car);
            }
        }
        csvWriterTool.updateCsvFile();

        return cars.getCarList();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setColumnMapping()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(CarFromCSV.class);
        String[] columns = new String[] {"id", "brand", "purchaseDate", "color"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
