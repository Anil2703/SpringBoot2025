package com.rocky.service;

import com.rocky.dao.CarsDaoImpl;
import com.rocky.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing car inventory operations.
 */
@Service
public class CarsService {

    @Autowired
    CarsDaoImpl carsDao;

    /**
     * Adds a new car to the inventory.
     *
     * @param brand the brand of the car
     * @param model the model of the car
     * @param year the year of the car
     * @return a String message indicating the result of the operation
     */
    public String addNewCar(String brand, String model, Integer year) {
        return carsDao.addNewCar(brand, model, year);
    }

    /**
     * Retrieves a car from the inventory by its model.
     *
     * @param model the model of the car to be fetched
     * @return the Car object containing the details of the car fetched by its model
     */
    public Car getCarByModel(String model) {
        return carsDao.getCarByModel(model);
    }

    /**
     * Retrieves the total number of cars in the inventory.
     *
     * @return a String message indicating the total number of cars
     */
    public String getTotalBusinessOrders() {
        return "Fetched no. of recs: " + carsDao.getRowcount();
    }

    /**
     * Retrieves all cars from the inventory.
     *
     * @return a list of Car objects containing the details of all cars in the inventory
     */
    public List<Car> getAllCars() {
        return carsDao.getAllCars();
    }
}