package com.example.redogroupsolo.services;

import com.example.redogroupsolo.entities.Car;
import com.example.redogroupsolo.repositories.CarRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final Logger logger = Logger.getLogger(CarService.class);
    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // LOGGER: ADD, UPDATE or DELETE

    // add car
    public String addCar(Car newCar) {

        if (!carRepository.existsByRegNr(newCar.getRegNr())) {
            carRepository.save(newCar);

            logger.info("\nAdmin added new car: " + newCar.getManifacturer() + " " + newCar.getModel() + " with registration number: " + newCar.getRegNr() + "\n");
            return "Car added: " + newCar.getManifacturer() + ", " + newCar.getModel();
        } else {

            logger.warn("\nERROR: Admin tried to add new car with registrations number that already exists\n");
            return "ERROR: Car registration number: " + newCar.getRegNr() + " number already exists";
        }
    }

    public String deleteCar(Car car) {
        Optional<Car> optionalCar = carRepository.findById(car.getId());

        if (optionalCar.isPresent()) {
            Car carToBeDeleted = optionalCar.get();

            logger.info("\nAdmin deleted car: " + carToBeDeleted.getManifacturer() + " " + carToBeDeleted.getModel() + " with registration number: " + carToBeDeleted.getRegNr() + "\n");
            carRepository.delete(carToBeDeleted);

            return "Car deleted: " + carToBeDeleted.getManifacturer() + ", " + carToBeDeleted.getModel();
        } else {
            logger.warn("\nERROR: Car could not be found with id: " + car.getId() + "\n");
            return "ERROR: Car could not be found with the id: " + car.getId();
        }
    }

    // Update car
    public String updateCar(Car carToBeUpdated) {
        Optional<Car> optionalCar = carRepository.findById(carToBeUpdated.getId());

        StringBuilder updateMessage = new StringBuilder();

        if (optionalCar.isPresent()) {

            Car existingCar = optionalCar.get();

            updateMessage.append("\nAdmin updated Car with id: " + existingCar.getId() + "\n");
            if (carToBeUpdated.getPricePerDay() != 0 || carToBeUpdated.getPricePerDay() == existingCar.getPricePerDay()) {
                existingCar.setPricePerDay(carToBeUpdated.getPricePerDay());
                updateMessage.append("Price per day changed to: " + existingCar.getPricePerDay() + "\n");
            }
            if (carToBeUpdated.getManifacturer() != null && (!carToBeUpdated.getManifacturer().isEmpty() || !carToBeUpdated.getManifacturer().contains(existingCar.getManifacturer()))) {
                existingCar.setManifacturer(carToBeUpdated.getManifacturer());
                updateMessage.append("Manifacturer changed to: " + existingCar.getManifacturer() + "\n");
            }
            if (carToBeUpdated.getModel() != null && (!carToBeUpdated.getModel().isEmpty() || !carToBeUpdated.getModel().contains(existingCar.getModel()))) {
                existingCar.setModel(carToBeUpdated.getModel());
                updateMessage.append("Model changed to: " + existingCar.getModel() + "\n");
            }
            if (carToBeUpdated.getRegNr() != null && (!carToBeUpdated.getRegNr().isEmpty() || !carToBeUpdated.getRegNr().contains(existingCar.getRegNr()))) {
                existingCar.setRegNr(carToBeUpdated.getRegNr());
                updateMessage.append("Reg Nr changed to: " + existingCar.getRegNr() + "\n");
            }
            if (carToBeUpdated.getAvailable() != null) {
                if (!existingCar.getAvailable() && carToBeUpdated.getAvailable()) {
                    existingCar.setAvailable(true);
                    updateMessage.append("Changed availability to: true\n");
                } else if (existingCar.getAvailable() && !carToBeUpdated.getAvailable()) {
                    existingCar.setAvailable(false);
                    updateMessage.append("Changed availability to: false\n");
                }
            } else {

                // detta ska bort
            }

            logger.info(updateMessage);
            carRepository.save(existingCar);
            return updateMessage.toString();
        } else {
            logger.warn("\nERROR: Admin tried to update Car with ID that does not exist: " + carToBeUpdated.getId() + "\n");
            return "Provided Car ID could not be found";
        }
    }

    // List all cars
    public List<Car> allCars() {
        return carRepository.findAll();
    }

    // List of all Available cars
    public List<Car> allAvailableCars() {
        List availableCars = new ArrayList<>();

        for (Car car : carRepository.findAll()) {
            if (car.getAvailable()) {
                availableCars.add(car);
            }
        }

        return availableCars;
    }
}
