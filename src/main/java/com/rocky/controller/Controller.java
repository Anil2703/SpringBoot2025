package com.rocky.controller;

import com.rocky.model.Car;
import com.rocky.service.CarsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rocky.constants.Constants.GREET_MESSAGE;

@RestController
@Tag(name = "Car API", description = "API endpoints for managing car inventory")
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
    @Operation(summary = "Get default greeting message", description = "Returns a predefined greeting message")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved greeting message")
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
    @Operation(summary = "Get total number of cars", description = "Retrieves the total count of cars in inventory")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved total cars count")
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
    @Operation(summary = "Retrieve all cars", description = "Retrieves all cars from the inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all cars",
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Car.class))),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
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
    @Operation(summary = "Retrieve car by model", description = "Retrieves a specific car by its model name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved car",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
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
    @Operation(summary = "Add a new car", description = "Adds a new car to the inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added car"),
            @ApiResponse(responseCode = "400", description = "Invalid car data")
    })
    public String addNewCar(@RequestBody @Valid Car car) {
        System.out.println("request recieved at Controller-- addCarToInventory");
        return carsService.addNewCar(car.getBrand(), car.getModel(), car.getYear());
    }
}
