package com.rocky.service;

import com.rocky.dao.CarsDaoImpl;
import com.rocky.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarsService {

    @Autowired
    CarsDaoImpl carsDao;

    public String addNewCar(String brand, String model, Integer year) {
        return carsDao.addNewCar(brand, model, year);
    }


    public Car getCarByModel() {
        return carsDao.getCarByModel();
    }

    public String getTotalBusinessOrders() {
        return "Fetched no. of recs: " + carsDao.getRowcount();
    }

}
