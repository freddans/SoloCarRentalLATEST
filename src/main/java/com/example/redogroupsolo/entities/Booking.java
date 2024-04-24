package com.example.redogroupsolo.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "orders")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private Date hiringDate;
    private Date dueDate;

    public Booking() {
    }

    public Booking(Car car, Customer customer) {
        this.car = car;
        this.customer = customer;
        this.hiringDate = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Date hiringdate) {
        this.hiringDate = hiringdate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date duedate) {
        this.dueDate = duedate;
    }
}
