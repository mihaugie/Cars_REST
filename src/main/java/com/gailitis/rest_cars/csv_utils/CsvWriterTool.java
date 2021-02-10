package com.gailitis.rest_cars.csv_utils;

import au.com.bytecode.opencsv.CSVWriter;
import com.gailitis.rest_cars.dto.Cars;
import com.gailitis.rest_cars.model.Car;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@Data
public class CsvWriterTool {

    private final Cars cars;

    public void updateCsvFile() throws IOException,
            CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException {
        try (
//                Writer writer = Files.newBufferedWriter(Paths.get(CsvConsts.DATA_FILE_TO_SAVE_DATA_PATH));
                Writer writer = new OutputStreamWriter(new FileOutputStream(CsvConsts.DATA_FILE_TO_SAVE_DATA_PATH, false), StandardCharsets.ISO_8859_1);
        ) {
            final CustomMappingStrategy<Car> mappingStrategy = new CustomMappingStrategy<>();
            mappingStrategy.setType(Car.class);

            StatefulBeanToCsv<Car> beanToCsv = new StatefulBeanToCsvBuilder<Car>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mappingStrategy)
                    .build();

            List<Car> carList = cars.getCarList();
            beanToCsv.write(carList);
            writer.close();
        }
    }
}
