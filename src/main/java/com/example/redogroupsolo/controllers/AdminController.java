package com.example.redogroupsolo.controllers;

import com.example.redogroupsolo.entities.Booking;
import com.example.redogroupsolo.entities.Car;
import com.example.redogroupsolo.entities.Customer;
import com.example.redogroupsolo.services.BookingService;
import com.example.redogroupsolo.services.CarService;
import com.example.redogroupsolo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private CustomerService customerService;
    private CarService carService;
    private BookingService bookingService;

    @Autowired
    public AdminController(CustomerService customerService, CarService carService, BookingService bookingService) {
        this.customerService = customerService;
        this.carService = carService;
        this.bookingService = bookingService;
    }

    // Lista kunder
    @GetMapping("/api/v1/customers")
    public ResponseEntity<List> listAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // Lägg till fordon
    @PostMapping("/api/v1/addcar")
    public ResponseEntity<String> addCar(@RequestBody Car newCar) {
        return ResponseEntity.ok(carService.addCar(newCar));
    }

    // Delete
    @DeleteMapping("/api/v1/deletecar")
    public ResponseEntity<String> deleteCar(@RequestBody Car carToBeDeleted) {
        return ResponseEntity.ok(carService.deleteCar(carToBeDeleted));
    }

    // Update car
    @PutMapping("/api/v1/updatecar")
    public ResponseEntity<String> updateCar(@RequestBody Car carToBeUpdated) {
        return ResponseEntity.ok(carService.updateCar(carToBeUpdated));
    }

    // Add customer
    @PostMapping("/api/v1/addcustomer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer newCustomer) {
        return ResponseEntity.ok(customerService.addCustomer(newCustomer));
    }

    // Update customer
    @PutMapping("/api/v1/updatecustomer")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customerToBeUpdated) {
        return ResponseEntity.ok(customerService.updateCustomer(customerToBeUpdated));
    }

    // Delete customer
    @DeleteMapping("/api/v1/deletecustomer")
    public ResponseEntity<String> deleteCustomer(@RequestBody Customer customerToBeDeleted) {
        return ResponseEntity.ok(customerService.deleteCustomer(customerToBeDeleted));
    }

    // Get all cars
    @GetMapping("/api/v1/allcars")
    public ResponseEntity<List> listAllCars() {
        return ResponseEntity.ok(carService.allCars());
    }

    // Get all orders
    @GetMapping("/api/v1/orders")
    public ResponseEntity<List> listAllOrders() {
        return ResponseEntity.ok(bookingService.allBookings());
    }

    // Delete order
    @DeleteMapping("/api/v1/deleteorder")
    public ResponseEntity<String> deleteOrder(@RequestBody Booking bookingToBeDeleted) {
        return ResponseEntity.ok(bookingService.deleteOrder(bookingToBeDeleted));
    }
}
