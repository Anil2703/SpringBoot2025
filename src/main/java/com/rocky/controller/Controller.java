package com.rocky.controller;

import com.rocky.model.Car;
import com.rocky.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.rocky.constants.Constants.GREET_MESSAGE;

@RestController
public class Controller {

    @Autowired
    CarsService carsService;

    @GetMapping("/defaultMessage")
    public String defaultMessage() {
        return GREET_MESSAGE;
    }


    @GetMapping("/totalBusinessOrders")
    public String getTotalBusinessOrders() {
        System.out.println("Fetched no. of recs: " + carsService.getTotalBusinessOrders());
        return carsService.getTotalBusinessOrders();
    }

    @GetMapping("/getCarByModel")
    public Car getCarByModel() {
        System.out.println("Car Fetched: " + carsService.getCarByModel());
        return carsService.getCarByModel();
    }

    /**
     * Adds a new car to the inventory.
     *
     * This method handles HTTP POST requests to the /addCarToInventory endpoint.
     * It receives a Car object in the request body and passes the car details
     * to the CarsService to add the new car to the inventory.
     *
     * @param car the Car object containing the details of the car to be added
     * @return a String message indicating the result of the operation
     */
    @PostMapping("/addCarToInventory")
    public String addNewCar(@RequestBody Car car) {
        System.out.println("request recieved at Controller-- addCarToInventory");
        return carsService.addNewCar(car.getBrand(), car.getModel(), car.getYear());
    }
}
