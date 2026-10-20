package com.beginsecure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileLoaderTest {
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
    }
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void loadValidCustomerTest() {
        FileLoader fileLoader = new FileLoader();

        ArrayList<Customer> customers = fileLoader.loadCustomer("validCustomer.txt");

        Customer customer = customers.getFirst();

        assertEquals("Yoan Lazo", customer.getName());
        assertEquals("3212024151", customer.getPhone());
        assertEquals(150.00, customer.getInvoiceDue());
        assertEquals(LocalDate.of(2026,10,10),customer.getLastServiceDate());
        assertEquals(LocalDate.of(2026,12,12),customer.getNextServiceDate());
        assertEquals(20.00, customer.getLastServiceCost());
    }
    @Test
    void emptyCustomerTest() {
        FileLoader fileLoader = new FileLoader();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        ArrayList<Customer> customers = fileLoader.loadCustomer("empty.txt");

        assertTrue(customers.isEmpty());
        assertTrue(output.toString().contains("Text file contained no valid lines"));
    }
    @Test
    void fileNotFoundTest() {
        FileLoader loader = new FileLoader();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        loader.loadCustomer("doesNotExist.txt");

        assertTrue(output.toString().contains("Text file not found"));
    }
    @Test
    public void invalidPhoneTest() {
        FileLoader loader = new FileLoader();

        ArrayList<Customer> customers = loader.loadCustomer("invalidPhone.txt");

        assertTrue(customers.isEmpty());
    }
    @Test
    public void invalidInvoiceTest() {
        FileLoader loader = new FileLoader();

        ArrayList<Customer> customers = loader.loadCustomer("invalidInvoice.txt");

        assertTrue(customers.isEmpty());
    }
    @Test
    void invalidNextServiceTest() {
        FileLoader loader = new FileLoader();

        ArrayList<Customer> customers = loader.loadCustomer("invalidNextService.txt");

        assertTrue(customers.isEmpty());
    }
    @Test
    void invalidLastServiceTest() {
        FileLoader loader = new FileLoader();

        ArrayList<Customer> customers = loader.loadCustomer("invalidLastService.txt");

        assertTrue(customers.isEmpty());
    }
    @Test
    void invalidCustomerTest() {
        FileLoader loader = new FileLoader();

        ArrayList<Customer> customers = loader.loadCustomer("invalidCustomer.txt");

        Customer customer = customers.getFirst();

        assertEquals("Yoan Lazo", customer.getName());
        assertEquals("3212024151", customer.getPhone());
        assertEquals(150.00, customer.getInvoiceDue());
        assertEquals(LocalDate.of(2026,10,10),customer.getLastServiceDate());
        assertEquals(LocalDate.of(2026,12,12),customer.getNextServiceDate());
        assertEquals(20.00, customer.getLastServiceCost());
    }
}