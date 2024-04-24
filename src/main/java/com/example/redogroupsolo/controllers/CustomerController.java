package com.example.redogroupsolo.controllers;

import com.example.redogroupsolo.entities.Booking;
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
public class CustomerController {

    private CarService carService;
    private BookingService bookingService;

    @Autowired
    public CustomerController(CarService carService, BookingService bookingService) {
        this.carService = carService;
        this.bookingService = bookingService;
    }

    @GetMapping("/api/v1/cars")
    public ResponseEntity<List> allAvailableCars() {
        return ResponseEntity.ok(carService.allAvailableCars());
    }

    // EJ klar ännu
    @PostMapping("/api/v1/ordercar")
    // public ResponseEntity<String> orderCar(String username, String regNr) {
    public ResponseEntity<String> orderCar(@RequestParam int customerId, @RequestParam int carId) {
        return ResponseEntity.ok(bookingService.orderCar(customerId, carId));
    }

    // Avboka
    @PutMapping("/api/v1/cancelorder")
    public ResponseEntity<String> cancelOrder(@RequestBody Booking bookingId) {
        return ResponseEntity.ok(bookingService.cancelOrder(bookingId));
    }

    // myOrders
    @GetMapping("/api/v1/myorders")
    public ResponseEntity<List> myOrders(@RequestBody Customer ordersByUsername) {
        return ResponseEntity.ok(bookingService.customerOrderList(ordersByUsername));
    }
}
