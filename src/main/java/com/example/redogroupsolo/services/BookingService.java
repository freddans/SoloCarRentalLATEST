package com.example.redogroupsolo.services;

import com.example.redogroupsolo.entities.Booking;
import com.example.redogroupsolo.entities.Car;
import com.example.redogroupsolo.entities.Customer;
import com.example.redogroupsolo.repositories.BookingRepository;
import com.example.redogroupsolo.repositories.CarRepository;
import com.example.redogroupsolo.repositories.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private Logger logger = Logger.getLogger(BookingService.class);
    private BookingRepository bookingRepository;
    private CarRepository carRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, CarRepository carRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }

    public void saveOrder(Booking booking) {
            bookingRepository.save(booking);
    }

    public List<Booking> allBookings() {
        return bookingRepository.findAll();
    }

    public String deleteOrder(Booking booking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(booking.getId());

        if (optionalBooking.isPresent()) {
            Booking bookingToBeDeleted = optionalBooking.get();

            // Change car to available
            Optional<Car> optionalCar = carRepository.findById(bookingToBeDeleted.getCar().getId());
            Optional<Customer> optionalCustomer = customerRepository.findById(bookingToBeDeleted.getCar().getId());

            if (optionalCar.isPresent() && optionalCustomer.isPresent()) {
                Car car = optionalCar.get();
                Customer customer = optionalCustomer.get();

                car.setAvailable(true);

                carRepository.save(car);

                logger.info("\nAdmin deleted order id: " + booking.getId() + " with customer: " + customer.getUsername() + " and car regnr: " + car.getRegNr() + "\n");
                bookingRepository.delete(bookingToBeDeleted);

                return "Order deleted: " + booking.getId();
            } else {
                logger.warn("\nERROR: Either provided Customer ID or Car ID does not exist");

                return "ERROR: Either provided Customer ID or Car ID does not exist";
            }

        } else {
            logger.warn("\nERROR: Order could not be found with id: " + booking.getId() + "\n");
            return "ERROR: Order could not be found with the id: " + booking.getId();
        }
    }

    public String orderCar(int customerId, int carId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Optional<Car> optionalCar = carRepository.findById(carId);

        if (optionalCar.isPresent() && optionalCar.isPresent()) {
            Customer customer = optionalCustomer.get();
            Car car = optionalCar.get();

            if (car.getAvailable()) {

                car.setAvailable(false);

                Booking booking = new Booking(car, customer);

                logger.info("\nCustomer " + booking.getCustomer().getUsername() + " successfully ordered car: " + booking.getCar().getRegNr() + "\n");

                saveOrder(booking);

                return "Customer " + booking.getCustomer().getUsername() + " booked car: " + booking.getCar().getRegNr();
            } else {

                logger.warn("\nERROR: Car is already booked and is unavailable\n");
                return "ERROR: Car already booked";
            }

        } else {
            logger.warn("\nERROR: Provided Customer ID or Car ID does not exist\n");
            return "ERROR: Provided Customer ID or Car ID does not exist";
        }
    }

    public String cancelOrder(Booking bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId.getId());

        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();

            booking.setDueDate(new Date());

            booking.getCar().setAvailable(true);

            saveOrder(booking);

            logger.info("\nCustomer " + booking.getCustomer().getUsername() + " canceled their order for: " + booking.getCar().getRegNr() + "\n");

            return "Customer " + booking.getCustomer().getUsername() + " canceled their order for: " + booking.getCar().getRegNr();
        } else {
            logger.warn("\nERROR: Customer tried to cancel order where order id does not exist\n");
            return "ERROR: Customer tried to cancel order where order id does not exist";
        }
    }

    public List<Booking> customerOrderList(Customer usernameOfCustomer) {
        List theList = new ArrayList<>();

        Customer theCustomer = customerRepository.findByUsername(usernameOfCustomer.getUsername());

        for (Booking booking : bookingRepository.findAll()) {
            if (booking.getCustomer().getUsername().toLowerCase().contains(theCustomer.getUsername().toLowerCase())) {
                theList.add(booking);
            }
        }

        return theList;
    }
}
