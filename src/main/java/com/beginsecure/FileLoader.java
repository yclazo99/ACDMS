package com.beginsecure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/*
Yoan Lazo
Software Development 1
CEN 3024C 13038

This class handles all file loading procedures. Only used by DMS class.
 */
public class FileLoader {

    /*
    ------------------------------------------------------------------------------------------------------------------
    Name: loadCustomer
    Purpose: Uses an ArrayList to temporarily hold validated values read from valid from text files. Passes Arraylist to
    DMS for handling.
    Arguments: a String containing a path to the text file.
    Returns: An ArrayList called textCustomers containing valid values.
    Notes:
     */
    public ArrayList<Customer> loadCustomer(String filePath) {
        ArrayList<Customer> textCustomers = new ArrayList<>();

        //Reading, validating, and assigning to temporary array list from text file -----
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                String[] parts = line.split("~");

                //Checks for extra or missing values and skips line if found----
                if (parts.length != 9) {
                    System.out.println("(Line " + lineNumber + "): Wrong number of values");
                    continue;
                }

                boolean missingValue = false;

                for (String part : parts) {
                    if (part.trim().isEmpty()) {
                        missingValue = true;
                        break;
                    }
                }

                if (missingValue) {
                    System.out.println("(Line " + lineNumber + "): Value missing");
                    continue;
                }

                //Parts of the text file----------------------------------
                try {
                    String name = parts[0].trim();
                    String address = parts[1].trim();
                    String phone = parts[2].trim();
                    String unitType = parts[3].trim();
                    LocalDate nextServiceDate = LocalDate.parse(parts[4].trim());
                    double invoiceDue = Double.parseDouble(parts[5].trim());
                    String lastService = parts[6].trim();
                    LocalDate lastServiceDate = LocalDate.parse(parts[7].trim());
                    double lastServiceCost = Double.parseDouble(parts[8].trim());

                    //Validation of individual values-----------------------
                    if (!name.matches("[a-zA-Z ]+")) {
                        System.out.println("(Line " + lineNumber + "): Invalid name");
                        continue;
                    }

                    if (!phone.matches("\\d{10}")) {
                        System.out.println("(Line " + lineNumber + "): Invalid phone number");
                        continue;
                    }
                    boolean duplicate = false;
                    for (Customer customer : textCustomers) {

                        if (customer.getPhone().equals(phone)) {
                            duplicate = true;
                            break;
                        }
                    }
                    if (duplicate) {
                        System.out.println("(Line " + lineNumber + "): Duplicate phone number");
                        continue;
                    }

                    if (nextServiceDate.isBefore(LocalDate.now())) {
                        System.out.println("(Line " + lineNumber + "): Next service date is in the past");
                        continue;
                    }
                    if (invoiceDue < 0 || lastServiceCost < 0) {
                        System.out.println("(Line " + lineNumber + "): Negative invoice amount");
                        continue;
                    }
                    if (lastServiceDate.isAfter(LocalDate.now())) {
                        System.out.println("(Line " + lineNumber + "): Last service date cannot be in the future");
                        continue;
                    }

                    textCustomers.add(new Customer(
                            name,
                            address,
                            phone,
                            unitType,
                            nextServiceDate,
                            invoiceDue,
                            lastService,
                            lastServiceDate,
                            lastServiceCost
                    ));

                } catch (DateTimeParseException | NumberFormatException e) {
                    System.out.println("(Line " + lineNumber + "): Invalid data format");
                    continue;
                }

            }

        } catch (IOException e) {
            System.out.println("\nText file not found");
            return textCustomers;
        }

        if (textCustomers.isEmpty()) {
            System.out.println("Text file contained no valid lines");
        }else {
            System.out.println("Customer(s) successfully loaded");
        }

        return textCustomers;
    }
}
