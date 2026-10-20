package com.beginsecure;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/*
Yoan Lazo
Software Development 1
CEN 3024C 13038

This class is used for the creation of the customer object that is stored in the database. It is used by DMS
and FileLoader to create customer objects.
 */
public class Customer {

    /*
    Name: toString
    Purpose: Formats customer objects into a readable String.
    Arguments:
    Returns: String containing all customer information.
    Notes: Overrides object's default toString method.
     */
    @Override
    public String toString() {
        return "Name: " + name +
                "\nAddress: " + address +
                "\nPhone: " + phone +
                "\nUnit Type: " + unitType +
                "\nNext Service Date: " + nextServiceDate +
                "\nInvoice Due: $" + invoiceDue +
                "\nLast Service: " + lastService +
                "\nLast Service Date: " + lastServiceDate +
                "\nLast Service Cost: $" + lastServiceCost +
                "\nDays Since Last Service: " + daysSinceLastService +
                "\n";
    }

    private String name;
    private String address;
    private String phone;
    private String unitType;
    private LocalDate nextServiceDate;
    private double invoiceDue;
    private String lastService;
    private LocalDate lastServiceDate;
    private double lastServiceCost;
    private int daysSinceLastService;


    public Customer(){
    }

    //Secondary Constructor
    public Customer(String name, String address, String phone,
                    String unitType, LocalDate nextServiceDate,
                    double invoiceDue, String lastService,
                    LocalDate lastServiceDate,
                    double lastServiceCost) {
        this.name = name;
        this.address = address;
        this.unitType = unitType;
        this.phone = phone;
        this.nextServiceDate = nextServiceDate;
        this.invoiceDue = invoiceDue;
        this.lastService = lastService;
        this.lastServiceDate = lastServiceDate;
        this.lastServiceCost = lastServiceCost;
        this.daysSinceLastService = this.calculateDaysSinceLastService();
    }

    //SETTERS---------------------------------------------------------------------------
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
    public void setNextServiceDate(LocalDate nextService) {
        this.nextServiceDate = nextService;
    }
    public void setInvoiceDue(double invoiceDue) {
        this.invoiceDue = invoiceDue;
    }
    public void setLastService(String lastService) {
        this.lastService = lastService;
    }
    public void setLastServiceDate(LocalDate lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }
    public void setLastServiceCost(double lastServiceCost) {
        this.lastServiceCost = lastServiceCost;
    }
    public void setDaysSinceLastService(int daysSinceLastService) {
        this.daysSinceLastService = daysSinceLastService;
    }
    //GETTERS-----------------------------------------------------------------------------
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    public String getUnitType() {
        return unitType;
    }
    public LocalDate getNextServiceDate() {
        return nextServiceDate;
    }
    public double getInvoiceDue() {
        return invoiceDue;
    }
    public String getLastService() {
        return lastService;
    }
    public LocalDate getLastServiceDate() {
        return lastServiceDate;
    }
    public double getLastServiceCost() {
        return lastServiceCost;
    }
    public int getDaysSinceLastService() {
        return daysSinceLastService;
    }

    //CUSTOM ACTION
    public int calculateDaysSinceLastService() {
        return (int) ChronoUnit.DAYS.between(this.lastServiceDate, LocalDate.now());
    }
}
