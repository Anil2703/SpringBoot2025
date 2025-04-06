package com.rocky.dao;


import com.rocky.Application;
import com.rocky.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("CarsDaoImpl")
public class CarsDaoImpl {

    private static final Logger logger = LoggerFactory.getLogger(CarsDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CarsDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int getRowcount(){
        return jdbcTemplate.queryForObject("select count(*) from Cars", new Object[]{},Integer.class);
    }

    public Car getCarByModel() {
        return jdbcTemplate.queryForObject("select * from Cars", new Object[]{}, new CarMapper());
    }

    public String addNewCar(String brand, String model, Integer year) {

        try {
            String sql = "INSERT INTO cars (brand, model, year) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, brand, model, year);
            return "Car added to Inventory";
        } catch (Exception e) {
            logger.error("An error occurred while adding NewCar to Inventory: {}", e.getMessage());
        }
        return "Error occured while adding NewCar to Inventory";
    }
}
