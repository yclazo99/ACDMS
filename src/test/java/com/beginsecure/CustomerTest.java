package com.beginsecure;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void setNameTest() {
        Customer customer = new Customer();
        customer.setName("Yoan");
        assertEquals("Yoan", customer.getName());
    }

    @Test
    void setAddressTest() {
        Customer customer = new Customer();
        customer.setAddress("1201 Oak Haven Dr");
        assertEquals("1201 Oak Haven Dr", customer.getAddress());
    }

    @Test
    void setUnitTypeTest() {
        Customer customer = new Customer();
        customer.setUnitType("Sony");
        assertEquals("Sony", customer.getUnitType());
    }

    @Test
    void setPhoneTest() {
        Customer customer = new Customer();
        customer.setPhone("123456789");
        assertEquals("123456789", customer.getPhone());
    }

    @Test
    void setNextServiceTest() {
        Customer customer = new Customer();
        customer.setNextServiceDate(LocalDate.of(2026, 10, 19));
        assertEquals(LocalDate.of(2026, 10, 19), customer.getNextServiceDate());
    }

    @Test
    void setInvoiceDueTest() {
        Customer customer = new Customer();
        customer.setInvoiceDue(500.99);
        assertEquals(500.99, customer.getInvoiceDue());
    }

    @Test
    void setLastServiceTest() {
        Customer customer = new Customer();
        customer.setLastService("Cleaning");
        assertEquals("Cleaning", customer.getLastService());
    }

    @Test
    void setLastServiceDateTest() {
        Customer customer = new Customer();
        customer.setLastServiceDate(LocalDate.of(2026, 10, 19));
        assertEquals(LocalDate.of(2026, 10, 19), customer.getLastServiceDate());
    }

    @Test
    void setLastServiceCostTest() {
        Customer customer = new Customer();
        customer.setLastServiceCost(500.99);
        assertEquals(500.99, customer.getLastServiceCost());
    }

    @Test
    void setDaysSinceLastServiceTest() {
        Customer customer = new Customer();
        customer.setDaysSinceLastService(1);
        assertEquals(1, customer.getDaysSinceLastService());
    }

    /*This test needs to be updated for daily change
    @Test
    void calculateDaysSinceLastService() {
        Customer customer = new Customer();
        customer.setLastServiceDate(LocalDate.of(2026, 10, 10));
        assertEquals(9, customer.calculateDaysSinceLastService());
    }
     */
}