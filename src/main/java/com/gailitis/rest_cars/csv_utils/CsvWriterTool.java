package com.gailitis.rest_cars.csv_utils;

import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import com.gailitis.rest_cars.dto.CarFromCSV;
import com.gailitis.rest_cars.dto.Cars;
import com.gailitis.rest_cars.model.Car;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvWriterTool {

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

            List<Car> carList = Cars.getInstance().getCarList();
            beanToCsv.write(carList);
            writer.close();
        }
    }



}
