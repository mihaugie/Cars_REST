package com.gailitis.rest_cars.dao;


import com.gailitis.rest_cars.model.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CarsDAOTest {

    @Autowired
    CarsDAO carsDAO = new CarsDAO();

    @Test
    public void getCarList() throws IOException {
        List<Car> carList = carsDAO.getCarList();
        assertThat(carList.isEmpty()).isFalse();
    }

    @Test
    public void whenFindById_thenReturnCar() throws IOException {
        Car carById = carsDAO.getCarById(1);
        assertThat(carById.getBrand()).isEqualTo("Ford fiesta");
    }

    @Test
    public void whenCreateCar_thenReturnCar() throws IOException {
        Car testCar = new Car(12, "Test car", "2.2.2222", "Test color");
        carsDAO.addCar(testCar);
        assertThat(carsDAO.getCarList().contains(testCar));
    }
}