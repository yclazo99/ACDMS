package com.beginsecure;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DMSTest {

    /*Runs Indefinitely waiting for user input. Must Switch to manual Test Case.
    @Test
    public void addCustomerManuallyTest() {
        DMS dms = new DMS();

        Customer customer = dms.addCustomerManually();

        assertEquals("Yoan Lazo", customer.getName());
        assertEquals("1201 Oak Haven Dr", customer.getAddress());
        assertEquals("3212024151", customer.getPhone());
        assertEquals("Yamaha1450", customer.getUnitType());
        assertEquals(LocalDate.of(2026,12,12),customer.getNextServiceDate());
        assertEquals(150.00, customer.getInvoiceDue());
        assertEquals("Cleaning", customer.getLastService());
        assertEquals(LocalDate.of(2026,10,10),customer.getLastServiceDate());
        assertEquals(20.00, customer.getLastServiceCost());
        assertEquals(9, customer.getDaysSinceLastService());
    } */

    @Test
    void addCustomerFromFileTest(){
        DMS dms = new DMS();
        ArrayList<Customer> customers = new ArrayList<>();
        customers = dms.addCustomerFromFile("invalidCustomer.txt");

        Customer customer = customers.getFirst();

        assertEquals("Yoan Lazo", customer.getName());
        assertEquals("3212024151", customer.getPhone());
        assertEquals(150.00, customer.getInvoiceDue());
        assertEquals(LocalDate.of(2026,10,10),customer.getLastServiceDate());
        assertEquals(LocalDate.of(2026,12,12),customer.getNextServiceDate());
        assertEquals(20.00, customer.getLastServiceCost());
    }

}