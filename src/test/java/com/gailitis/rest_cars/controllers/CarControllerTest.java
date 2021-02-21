package com.gailitis.rest_cars.controllers;

import com.gailitis.rest_cars.model.Car;
import com.gailitis.rest_cars.services.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarService carService;

    @Test
    public void whenGetCars_thenStatus200()
            throws Exception {

        mvc.perform(get("/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))))
                .andExpect(jsonPath("$[0].brand", is("Ford fiesta")))
                .andExpect(jsonPath("$[1].color", is("Czarny")));
    }

    @Test
    public void whenValidInput_thenCreateCar() throws Exception {
        String jsonNewCar = "{\n" +
                "    \"id\": 7,\n" +
                "    \"brand\": \"Polonez\",\n" +
                "    \"purchaseDate\": \"14.05.1986\",\n" +
                "    \"color\": \"Brudny\"\n" +
                "}";
        mvc.perform(post("/cars").contentType(MediaType.APPLICATION_JSON).content(jsonNewCar));
        List<Car> allCars = carService.getAllCars();
        assertThat(allCars).extracting(Car::getBrand).contains("Polonez");
    }

    @Test
    public void whenValidInput_thenUpdateCar() throws Exception {
        String jsonNewCar = "{\n" +
                "    \"id\": 7,\n" +
                "    \"brand\": \"Polonezzz\",\n" +
                "    \"purchaseDate\": \"14.05.1986\",\n" +
                "    \"color\": \"Brudny\"\n" +
                "}";
        mvc.perform(put("/cars/4").contentType(MediaType.APPLICATION_JSON).content(jsonNewCar));

        Car carWithId4 = carService.getCarById(4);
        assertThat(carWithId4.getBrand()).isEqualTo("Polonezzz");
    }
}