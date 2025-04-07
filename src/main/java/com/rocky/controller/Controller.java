package com.rocky.controller;

import com.rocky.model.Car;
import com.rocky.service.CarsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rocky.constants.Constants.GREET_MESSAGE;

@RestController
public class Controller {

    @Autowired
    CarsService carsService;

    /**
     * Returns the default greeting message.
     * <p>
     * This method handles HTTP GET requests to the /defaultMessage endpoint.
     * It returns a predefined greeting message from the constants.
     *
     * @return the default greeting message
     */
    @GetMapping("/defaultMessage")
    public String defaultMessage() {
        return GREET_MESSAGE;
    }

    /**
     * Retrieves the total number of cars in the inventory.
     *
     * This method handles HTTP GET requests to the /totalBusinessOrders endpoint.
     * It fetches the total number of cars from the CarsService and returns it as a String.
     *
     * @return a String message indicating the total number of cars in the inventory
     */
    @GetMapping("/totalBusinessOrders")
    public String getTotalBusinessOrders() {
        System.out.println("Fetched no. of cars: " + carsService.getTotalBusinessOrders());
        return carsService.getTotalBusinessOrders();
    }

    /**
     * Retrieves all cars from the inventory.
     * <p>
     * This method handles HTTP GET requests to the /getAllCars endpoint.
     * It fetches all car details from the CarsService and returns a list of Car objects.
     *
     * @return a list of Car objects containing the details of all cars in the inventory
     */
    @GetMapping("/getAllCars")
    public List<Car> getAllCars() {
        return carsService.getAllCars();
    }

    /**
     * Retrieves a car from the inventory by its model.
     * <p>
     * This method handles HTTP GET requests to the /getCarByModel endpoint.
     * It fetches the car details from the CarsService and returns the Car object.
     *
     * @return the Car object containing the details of the car fetched by its model
     */
    @GetMapping("/getCarByModel/{model}")
    public Car getCarByModel(@PathVariable("model") String model) {
        return carsService.getCarByModel(model);
    }

    /**
     * Adds a new car to the inventory.
     * <p>
     * This method handles HTTP POST requests to the /addCarToInventory endpoint.
     * It receives a Car object in the request body and passes the car details
     * to the CarsService to add the new car to the inventory.
     *
     * @param car the Car object containing the details of the car to be added
     * @return a String message indicating the result of the operation
     */
    @PostMapping("/addCarToInventory")
    public String addNewCar(@RequestBody @Valid Car car) {
        System.out.println("request recieved at Controller-- addCarToInventory");
        return carsService.addNewCar(car.getBrand(), car.getModel(), car.getYear());
    }
}
