package com.gailitis.rest_cars.csv_utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.gailitis.rest_cars.dto.CarFromCSV;
import com.gailitis.rest_cars.dto.Cars;
import com.gailitis.rest_cars.model.Car;
import com.gailitis.rest_cars.services.CarMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
public class CSVMappedToCars {

    private Cars cars;
    private CarMapper carMapper;
    private List<CarFromCSV> carList;


    @EventListener(ApplicationReadyEvent.class)
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void loadDataFromCSV() throws FileNotFoundException {
        CsvToBean csv = new CsvToBean();

        String csvFilename = CsvConsts.DATA_FILE_PATH;
        CSVReader csvReader = new CSVReader(new FileReader(csvFilename));

        carList = csv.parse(setColumnMapping(), csvReader);
        for (Object object : carList) {
            CarFromCSV carFromCSV = (CarFromCSV) object;
            System.out.println(carFromCSV);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    private void revert() throws InterruptedException, IOException {
        carMapper = new CarMapper();

        for (CarFromCSV csvCar: carList
             ) {
            Car car = carMapper.fromCarFromCSVToCar(csvCar);
            Cars.getInstance().uploadData(car);
        }
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
