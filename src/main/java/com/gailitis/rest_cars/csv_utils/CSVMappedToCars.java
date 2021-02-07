package com.gailitis.rest_cars.csv_utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        String csvFilename = "src\\main\\resources\\data_source\\samochody.csv";
        CSVReader csvReader = new CSVReader(new FileReader(csvFilename));

        carList = csv.parse(setColumnMapping(), csvReader);
        for (Object object : carList) {

            CarFromCSV carFromCSV = (CarFromCSV) object;
            System.out.println(carFromCSV);
        }

    }

    @EventListener(ApplicationReadyEvent.class)
    private void revert() throws InterruptedException {
        carMapper = new CarMapper();

        for (CarFromCSV csvCar: carList
             ) {
//            Car car = new Car(csvCar.getId(), csvCar.getBrand(), csvCar.getPurchaseDate(), csvCar.getColor());
            Car car = carMapper.fromCarFromCSVToCar(csvCar);
//            cars.addCar(carMapper.fromCarFromCSVToCar(csvCar));
            Cars.getInstance().addCar(car);
//            cars.addCar(car);

        }
//        Car car = carMapper.fromCarFromCSVToCar(carFromCSV);
//        cars.addCar(car);
    }


//    @SuppressWarnings({"rawtypes", "unchecked"})
//    public static void main(String[] args) throws Exception
//    {
//        CsvToBean csv = new CsvToBean();
//
//        String csvFilename = "src\\main\\resources\\data_source\\samochody.csv";
//        CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
//
//        //Set column mapping strategy
//        List listOfCars = csv.parse(setColumnMapping(), csvReader);
//        for (Object object : listOfCars) {
//            CarFromCSV carFromCSV = (CarFromCSV) object;
//            System.out.println(carFromCSV);
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
