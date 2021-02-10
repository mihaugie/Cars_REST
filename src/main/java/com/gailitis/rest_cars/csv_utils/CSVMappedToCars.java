package com.gailitis.rest_cars.csv_utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.gailitis.rest_cars.dto.CarFromCSV;
import com.gailitis.rest_cars.dto.Cars;
import com.gailitis.rest_cars.model.Car;
import com.gailitis.rest_cars.services.CarMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
@Data
//@NoArgsConstructor
public class CSVMappedToCars {

    private final Cars cars;
    private CarMapper carMapper;
    private List<CarFromCSV> carList = new ArrayList<>();


    @EventListener(ApplicationReadyEvent.class)
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void sss(){
//        System.out.println("dupa");
    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    public void loadDataFromCSV() throws FileNotFoundException {
////        System.out.println("dupa");
//
//        CsvToBean csv = new CsvToBean();
//        carMapper = new CarMapper();
////        cars = new Cars();
//
//        String csvFilename = CsvConsts.DATA_FILE_PATH;
//        CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
//
//        carList = csv.parse(setColumnMapping(), csvReader);
//        for (Object object : carList) {
//            CarFromCSV carFromCSV = (CarFromCSV) object;
//            System.out.println(carFromCSV);
//            Car car = carMapper.fromCarFromCSVToCar(carFromCSV);
//            cars.uploadData(car);
//
//        }
//    }

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
