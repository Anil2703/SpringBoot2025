package com.rocky.dao;


import com.rocky.exception.CarNotFoundException;
import com.rocky.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("CarsDaoImpl")
public class CarsDaoImpl {

    private static final Logger logger = LoggerFactory.getLogger(CarsDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CarsDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves the total number of cars in the inventory.
     * <p>
     * This method executes a SQL query to count the number of rows in the Cars table.
     *
     * @return the total number of cars in the inventory
     */
    public int getRowcount() {
        return jdbcTemplate.queryForObject("select count(*) from Cars", new Object[]{}, Integer.class);
    }

    /**
     * Retrieves a car from the inventory by its model.
     * <p>
     * This method executes a SQL query to fetch the car details from the Cars table.
     *
     * @return the Car object containing the details of the car fetched by its model
     */
    public Car getCarByModel(String model) {
        Car car = null;
        try {
            car = jdbcTemplate.queryForObject("select * from Cars where model = ?", new Object[]{model}, new CarMapper());
            logger.info("Car fetched from DB: {}", car);
        } catch (EmptyResultDataAccessException e) {
            logger.error("An error occurred while fetching car by model: {}", e.getMessage());
            throw new CarNotFoundException("Car not found of model: " + model);
        }
        return car;
    }

    /**
     * Adds a new car to the inventory.
     * <p>
     * This method executes a SQL query to insert a new car into the Cars table.
     *
     * @param brand the brand of the car
     * @param model the model of the car
     * @param year  the year of the car
     * @return a String message indicating the result of the operation
     */
    public String addNewCar(String brand, String model, Integer year) {
        try {
            String sql = "INSERT INTO cars (brand, model, year) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, brand, model, year);
            return "Car added to Inventory";
        } catch (Exception e) {
            logger.error("An error occurred while adding NewCar to Inventory: {}", e.getMessage());
        }
        return "Error occurred while adding NewCar to Inventory";
    }

    /**
     * Retrieves all cars from the inventory.
     * <p>
     * This method executes a SQL query to fetch all car details from the Cars table.
     *
     * @return a list of Car objects containing the details of all cars in the inventory
     */
    public List<Car> getAllCars() {
        String sql = "select * from Cars";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<Car> cars = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Car car = new Car();
            car.setBrand((String) row.get("brand"));
            car.setModel((String) row.get("model"));
            car.setYear((Integer) row.get("year"));
            cars.add(car);
        }
        logger.info("Cars fetched from DB: {}", cars);
        return cars;
    }
}
