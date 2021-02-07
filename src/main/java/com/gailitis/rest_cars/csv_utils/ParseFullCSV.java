package com.gailitis.rest_cars.csv_utils;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class ParseFullCSV {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception
    {
        //Build reader instance
        CSVReader reader = new CSVReader(new FileReader("src\\main\\resources\\data_source\\samochody.csv"), ',', '"', 1);

        //Read all rows at once
        List<String[]> allRows = reader.readAll();

        //Read CSV line by line and use the string array as you want
        for(String[] row : allRows){
            System.out.println(Arrays.toString(row));
        }
    }
}
