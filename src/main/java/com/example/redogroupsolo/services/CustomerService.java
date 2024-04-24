package com.example.redogroupsolo.services;

import com.example.redogroupsolo.entities.Customer;
import com.example.redogroupsolo.repositories.CarRepository;
import com.example.redogroupsolo.repositories.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final Logger logger = Logger.getLogger(CustomerService.class);
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Add customer
    public String addCustomer(Customer newCustomer) {
        String username = newCustomer.getUsername();
        String email = newCustomer.getEmail();
        String phone = newCustomer.getPhone();

        // Check if username, email, or phone already exists
        if (customerRepository.existsByUsername(username)) {
            logger.warn("ERROR: Username already exists: " + username);
            return "ERROR: Username already exists: " + username;
        }

        if (customerRepository.existsByEmail(email)) {
            logger.warn("ERROR: Email already exists: " + email);
            return "ERROR: Email already exists: " + email;
        }

        if (customerRepository.existsByPhone(phone)) {
            logger.warn("ERROR: Phone number already exists: " + phone);
            return "ERROR: Phone number already exists: " + phone;
        }

        // If no existing record found, save the new customer
        customerRepository.save(newCustomer);
        logger.info("Admin added new customer: " + username);
        return "Customer added: " + username;
    }


    // Update customer
    public String updateCustomer(Customer customerToBeUpdated) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerToBeUpdated.getId());

        StringBuilder updateMessage = new StringBuilder();

        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();

            updateMessage.append("\nAdmin updated Customer with id: " + existingCustomer.getId() + "\n");
            if (customerToBeUpdated.getUsername() != null && (!customerToBeUpdated.getUsername().isEmpty() || !customerToBeUpdated.getUsername().contains(existingCustomer.getName()))) {
                existingCustomer.setUsername(customerToBeUpdated.getUsername());
                updateMessage.append("Username changed to: " + existingCustomer.getUsername() + "\n");
            }
            if (customerToBeUpdated.getName() != null && (!customerToBeUpdated.getName().isEmpty() || !customerToBeUpdated.getUsername().contains(existingCustomer.getName()))) {
                existingCustomer.setName(customerToBeUpdated.getName());
                updateMessage.append("Name changed to: " + existingCustomer.getName() + "\n");
            }
            if (customerToBeUpdated.getAddress() != null && (!customerToBeUpdated.getAddress().isEmpty() || !customerToBeUpdated.getAddress().contains(existingCustomer.getAddress()))) {
                existingCustomer.setAddress(customerToBeUpdated.getAddress());
                updateMessage.append("Address changed to: " + existingCustomer.getAddress() + "\n");
            }
            if (customerToBeUpdated.getEmail() != null && (!customerToBeUpdated.getEmail().isEmpty() || !customerToBeUpdated.getEmail().contains(existingCustomer.getEmail()))) {
                existingCustomer.setEmail(customerToBeUpdated.getEmail());
                updateMessage.append("Email changed to: " + existingCustomer.getEmail() + "\n");
            }
            if (customerToBeUpdated.getPhone() != null && (!customerToBeUpdated.getPhone().isEmpty() || !customerToBeUpdated.getPhone().contains(existingCustomer.getPhone()))) {
                existingCustomer.setPhone(customerToBeUpdated.getPhone());
                updateMessage.append("Phone number changed to: " + existingCustomer.getPhone() + "\n");
            }
            logger.info(updateMessage);
            customerRepository.save(existingCustomer);
            return updateMessage.toString();
        } else {
            logger.warn("\nERROR: Admin tried to update Customer with ID that does not exist: " + customerToBeUpdated.getId() + "\n");
            return "Provided Customer ID could not be found";
        }
    }

    // Delete customer
    public String deleteCustomer(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());

        if (optionalCustomer.isPresent()) {
            Customer customerToBeDeleted = optionalCustomer.get();

            logger.info("\nAdmin deleted customer: " + customerToBeDeleted.getUsername() + "\n");
            customerRepository.delete(customerToBeDeleted);

            return "Customer deleted: " + customerToBeDeleted.getUsername();
        } else {
            logger.warn("\nERROR: Customer could not be found with id: " + customer.getId() + "\n");
            return "ERROR: Customer could not be found with the id: " + customer.getId();
        }
    }
}
