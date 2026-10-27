package com.beginsecure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DMSTest {
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
    void displayMenuTest() {
        String fakeInput = "6";
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        System.setIn(new ByteArrayInputStream(fakeInput.getBytes()));
        DMS dms = new DMS();

        dms.displayMenu();

        String menuText = output.toString();

        assertTrue(menuText.contains("-------Main Menu--------"));
        assertTrue(menuText.contains("1. View Customers"));
        assertTrue(menuText.contains("2. Add Customer"));
        assertTrue(menuText.contains("6. Exit"));
    }

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

    @Test
    void addCustomerManuallyTest() {
        DMS dms = new DMS();

        Customer customer = dms.addCustomerManually(
                "Yoan Lazo",
                "1201 Oak Haven Dr",
                "3212024151",
                "Yamaha1450",
                LocalDate.of(2027, 12, 12),
                150.00,
                "Cleaning",
                LocalDate.of(2026, 10, 10),
                20.00);

        assertEquals("Yoan Lazo", customer.getName());
        assertEquals("1201 Oak Haven Dr", customer.getAddress());
        assertEquals("3212024151", customer.getPhone());
        assertEquals("Yamaha1450", customer.getUnitType());
        assertEquals(LocalDate.of(2027, 12, 12), customer.getNextServiceDate());
        assertEquals(150.00, customer.getInvoiceDue());
        assertEquals("Cleaning", customer.getLastService());
        assertEquals(LocalDate.of(2026, 10, 10), customer.getLastServiceDate());
        assertEquals(20.00, customer.getLastServiceCost());
    }

    @Test
    void removeCustomerTest() {
        String fakeInput = "1";
        System.setIn(new ByteArrayInputStream(fakeInput.getBytes()));
        DMS dms = new DMS();

        Customer customer = dms.addCustomerManually(
                "Yoan Lazo",
                "1201 Oak Haven Dr",
                "3212024151",
                "Yamaha1450",
                LocalDate.of(2027, 12, 12),
                150.00,
                "Cleaning",
                LocalDate.of(2026, 10, 10),
                20.00);

        dms.viewCustomers().add(customer);

        Customer removedCustomer = dms.removeCustomer("3212024151");

        assertNotNull(removedCustomer);
        assertEquals("3212024151", removedCustomer.getPhone());
        assertTrue(dms.viewCustomers().isEmpty());
    }

    @Test
    void checkDaysSinceLastServiceTest() {
        DMS dms = new DMS();

        Customer customer = new Customer(
                "Yoan Lazo",
                "1201 Oak Haven Dr",
                "3212024151",
                "Yamaha1450",
                LocalDate.of(2027, 12, 12),
                150.00,
                "Cleaning",
                LocalDate.now().minusDays(10),
                20.00
        );

        dms.viewCustomers().add(customer);

        int days = dms.checkDaysSinceLastService("3212024151");

        assertEquals(10, days);
    }

    @Test
    void editCustomerTest() {
        String fakeInput = "1\n" + "Jack Smith";
        System.setIn(new ByteArrayInputStream(fakeInput.getBytes()));
        DMS dms = new DMS();

        Customer customer = dms.addCustomerManually(
                        "Yoan Lazo",
                        "1201 Oak Haven Dr",
                        "3212024151",
                        "Yamaha1450",
                        LocalDate.of(2027,12,12),
                        150.00,
                        "Cleaning",
                        LocalDate.of(2026,10,10),
                        20.00);

        dms.viewCustomers().add(customer);

        Customer editedCustomer = dms.editCustomer("3212024151");

        assertEquals("Jack Smith", editedCustomer.getName());
        assertEquals("Jack Smith", dms.viewCustomers().getFirst().getName());
    }
}