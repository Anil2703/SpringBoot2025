package com.rocky.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Represents a car with brand, model, and year attributes.
 */
public class Car {

    @NotBlank(message = "Brand name cannot be blank")
    @Size(min = 2, max = 20, message = "Brand name must be between 2 and 20 characters")
    private String brand;

    @NotBlank(message = "Model name cannot be blank")
    private String model;

    @NotBlank(message = "Year cannot be blank")
    private Integer year;

    /**
     * Default constructor for Car.
     */
    public Car() {
    }

    /**
     * Gets the brand of the car.
     *
     * @return the brand of the car
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the car.
     *
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the model of the car.
     *
     * @return the model of the car
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the car.
     *
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the year of the car.
     *
     * @return the year of the car
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Sets the year of the car.
     *
     * @param year the year to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Returns a string representation of the car.
     *
     * @return a string representation of the car
     */
    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}