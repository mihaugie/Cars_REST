package com.gailitis.rest_cars.dto;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.gailitis.rest_cars.csv_utils.*;
import com.gailitis.rest_cars.model.Car;
import com.gailitis.rest_cars.services.CarMapper;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cars {

    private List<Car> carList = new ArrayList<>();
    private CsvUpdater csvUpdater = new CsvUpdater();
//    private CsvWriterTool csvWriterTool;
    private CarMapper carMapper;
    private List<CarFromCSV> csvCarList = new ArrayList<>();

    //    public static Cars getInstance() {
//        if (instance == null) {
//            instance = new Cars();
//        }
//        return instance;
//    }


    @EventListener(ApplicationReadyEvent.class)
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void loadDataFromCSV() throws FileNotFoundException {
        CsvToBean csv = new CsvToBean();
        carMapper = new CarMapper();

        String csvFilename = CsvConsts.DATA_FILE_PATH;
        CSVReader csvReader = new CSVReader(new FileReader(csvFilename));

        csvCarList = csv.parse(setColumnMapping(), csvReader);
        for (Object object : csvCarList) {
            CarFromCSV carFromCSV = (CarFromCSV) object;
            System.out.println(carFromCSV);
            Car car = carMapper.fromCarFromCSVToCar(carFromCSV);
            carList.add(car);

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




    public Car uploadData(Car car) {
        carList.add(car);
        return car;
    }

    public Car addCar(Car car) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        carList.add(car);
        updateCsvFile();
        return car;
    }

    public Car uploadCar(Car car) {
        carList.add(car);
        return car;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public boolean removeCarById(Car car) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        carList.remove(car);
        updateCsvFile();
        return carList.remove(car);
    }

    public Car updateCar(int id, Car car) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        Car updatedCar = carList.set(id, car);
        updateCsvFile();
        return updatedCar;
    }

    public void updateCsvFile() throws IOException,
            CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(CsvConsts.DATA_FILE_PATH));
        ) {
            final CustomMappingStrategy<Car> mappingStrategy = new CustomMappingStrategy<>();
            mappingStrategy.setType(Car.class);

            StatefulBeanToCsv<Car> beanToCsv = new StatefulBeanToCsvBuilder<Car>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mappingStrategy)
                    .build();

//            List<Car> carList = carList.getCarList();
            beanToCsv.write(carList);
            writer.close();
        }
    }
}
