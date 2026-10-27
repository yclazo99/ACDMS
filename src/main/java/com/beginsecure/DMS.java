package com.beginsecure;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/*
    Yoan Lazo
    Software Development 1
    CEN 3024C 13038

    This program, named the AirConditioning Database Management System (ACDMS), is designed to store, display modify a
    list of customers. It Stores several values such as Name, Address, Phone, Unit Type, Next Service Date, Invoice
    Due, and more. It is currently only console-based, with users navigating menus via console commands, and uses
    an internal memory. There is currently no external database.

    This class contains the main method and is responsible for the majority of the program's logic and UI.
    */
public class DMS {

    private final ArrayList<Customer> customers = new ArrayList<>();
    private final Scanner input = new Scanner(System.in);

    /*
    ----------------------------------------------------------------------------------------------------------------
    Name: phoneExists
    Purpose: Checks for a duplicate phone value as phone values are the only value that is unique per customer.
    Arguments: A String containing a phone number.
    Returns: A boolean of either true or false
    Notes: This method was added from refactoring duplication logic within other methods to increase simplicity.
     */
    private boolean phoneExists(String phone) {
        for (Customer customer : customers) {
            if (customer.getPhone().equals(phone)) {
                return true;
            }
        }

        return false;
    }

    /*
   ----------------------------------------------------------------------------------------------------------------
   Name: addCustomerManually
   Purpose: Creates and assigns new customer object based on arguments. Created for testing purposes.
   Arguments: String name,
           String address,
           String phone,
           String unitType,
           LocalDate nextServiceDate,
           double invoiceDue,
           String lastService,
           LocalDate lastServiceDate,
           double lastServiceCost
   Returns: A Customer object (customer).
   Notes:The following values must be valid to be accepted:
       Name: Cannot contain anything but letters.
       Phone: must be a unique set of 10 integers.
       NextServiceDate: Must be formated (yyyy-MM-dd) and must be a date in the future.
       InvoiceDue: Must be a double and cannot be negative.
       LastServiceDate: Must be formated (yyyy-MM-dd) and must be a date in the past.
       LastServiceCost: Must be a double and cannot be negative.
    */
    public Customer addCustomerManually(
            String name,
            String address,
            String phone,
            String unitType,
            LocalDate nextServiceDate,
            double invoiceDue,
            String lastService,
            LocalDate lastServiceDate,
            double lastServiceCost) {



        Customer customer = new Customer(
                name,
                address,
                phone,
                unitType,
                nextServiceDate,
                invoiceDue,
                lastService,
                lastServiceDate,
                lastServiceCost
        );

        System.out.println("Customer has been created!");

        return customer;
    }

    /*
    ----------------------------------------------------------------------------------------------------------------
    Name: addCustomerManually
    Purpose: Guides the user through the manual addition of a customer object. Checks validation.
    Arguments:
    Returns: A Customer object (customer).
    Notes:The following values must be valid to be accepted:
        Name: Cannot contain anything but letters.
        Phone: must be a unique set of 10 integers.
        NextServiceDate: Must be formated (yyyy-MM-dd) and must be a date in the future.
        InvoiceDue: Must be a double and cannot be negative.
        LastServiceDate: Must be formated (yyyy-MM-dd) and must be a date in the past.
        LastServiceCost: Must be a double and cannot be negative.
     */
    public Customer addCustomerManually() {
        String name;
        String address;
        String phone;
        String unitType;
        LocalDate nextServiceDate;
        double invoiceDue;
        String lastService;
        LocalDate lastServiceDate;
        double lastServiceCost;

        //Validation and Input-------------------------------------------------------------
        do {
            System.out.print("Name: ");
            name = input.nextLine().trim();

            if (!name.matches("[a-zA-Z ]+")) {
                System.out.println("Invalid name!");
            }

        } while (!name.matches("[a-zA-Z ]+"));

        System.out.print("Address: ");
        address = input.nextLine().trim();

        do {
            System.out.print("Unique 10 digit Phone #(only numbers): ");
            phone = input.nextLine().trim();

            if (!phone.matches("\\d{10}")) {
                System.out.println("Invalid phone number");
                continue;
            }

            if (phoneExists(phone)) {
                System.out.println("Phone number already in use!");
            }

        } while (phoneExists(phone) || !phone.matches("\\d{10}"));

        System.out.print("Unit Type: ");
        unitType = input.nextLine().trim();

        do {
            try {
                System.out.print("Next Service Date (yyyy-MM-dd): ");
                nextServiceDate = LocalDate.parse(input.nextLine().trim());

                if (nextServiceDate.isBefore(LocalDate.now())) {
                    System.out.println("Next service date is in the past");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
                nextServiceDate = null;
            }
        } while (nextServiceDate == null || nextServiceDate.isBefore(LocalDate.now()));

        do {
            try {
                System.out.print("Invoice Due: ");
                invoiceDue = Double.parseDouble(input.nextLine().trim());

                if (invoiceDue <= 0) {
                    System.out.println("Invoice Due must be greater than zero!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid invoice due.");
                invoiceDue = -1.0;
            }

        } while (invoiceDue < 0);

        System.out.print("Last Service: ");
        lastService = input.nextLine().trim();

        do {
            try {
                System.out.print("Last Service Date (yyyy-MM-dd): ");
                lastServiceDate = LocalDate.parse(input.nextLine().trim());

                if (lastServiceDate.isAfter(LocalDate.now())) {
                    System.out.println("Last service date cannot be in the future");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
                lastServiceDate = null;
            }

        } while (lastServiceDate == null || lastServiceDate.isAfter(LocalDate.now()));

        do {
            try {
                System.out.print("Last Service Cost: ");
                lastServiceCost = Double.parseDouble(input.nextLine().trim());

                if (lastServiceCost <= 0) {
                    System.out.println("Last service cost must be greater than zero!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid service cost.");
                lastServiceCost = -1.0;
            }

        } while (lastServiceCost < 0);


        Customer customer = new Customer(
                name,
                address,
                phone,
                unitType,
                nextServiceDate,
                invoiceDue,
                lastService,
                lastServiceDate,
                lastServiceCost
        );

        System.out.println("Customer has been created!");

        return customer;
    }

    /*
   -----------------------------------------------------------------------------------------------------------------
   Name: addCustomerFromFile
   Purpose: Adds customers from valid text files.
   Arguments: A String containing the path to a text file.
   Returns: An ArrayType<Customer> from the FileLoader class
   Notes: The format I used for text files was as follows:
   "Yoan Lazo~1201 Oak Haven Dr, Altamonte, 32714~3212024151~Yamaha1450~2026-12-12~150.00~Cleaning~2026-10-10~20.00"
    The ~ key is used to split the line into parts.
    The following values must be valid to be accepted:
        Name[0]: Cannot contain anything but letters.
        Phone[2]: must be a unique set of 10 integers.
        NextServiceDate[4]: Must be formated (yyyy-MM-dd) and must be a date in the future.
        InvoiceDue[5]: Must be a double and cannot be negative.
        LastServiceDate[7]: Must be formated (yyyy-MM-dd) and must be a date in the past.
        LastServiceCost[8]: Must be a double and cannot be negative.

    */
    public ArrayList<Customer> addCustomerFromFile(String filePath) {
        FileLoader fileLoader = new FileLoader();
        return fileLoader.loadCustomer(filePath);
    }

    /*
   ------------------------------------------------------------------------------------------------------------------
   Name: editCustomer
   Purpose: Modifies any value of an existing customer using their unique phone number.
   Arguments: A String containing a phone number.
   Returns: A customer object.
   Notes: Returns null if not found.
    */
    public Customer editCustomer(String phoneNumber) {

        //Checks for existing Customer----------------------------------------------
        for (Customer customer : customers) {
            if (customer.getPhone().equals(phoneNumber)) {
                System.out.println("Customer found: " + customer.getName());
                System.out.println("Select field you wish to edit!");
                System.out.println("1. Name");
                System.out.println("2. Address");
                System.out.println("3. Phone");
                System.out.println("4. Unit Type");
                System.out.println("5. Next Service Date");
                System.out.println("6. Invoice Due");
                System.out.println("7. Last Service");
                System.out.println("8. Last Service Date");
                System.out.println("9. Last Service Cost");

                //Choice input validation--------------------------------------------
                int choice = 0;

                do {
                    try {
                        System.out.print("Choice: ");
                        choice = Integer.parseInt(input.nextLine());

                        if (choice < 1 || choice > 9) {
                            System.out.println("Invalid choice!");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a number.");
                    }

                } while (choice < 1 || choice > 9);

                //Handles choice logic and validation--------------------------------
                switch (choice) {
                    case 1:
                        String name;
                        do {
                            System.out.print("Name: ");
                            name = input.nextLine().trim();

                            if (!name.matches("[a-zA-Z ]+")) {
                                System.out.println("Invalid name!");
                            }

                        } while (!name.matches("[a-zA-Z ]+"));

                        customer.setName(name);
                        break;

                    case 2:
                        String address;
                        System.out.print("Address: ");
                        address = input.nextLine().trim();

                        customer.setAddress(address);
                        break;

                    case 3:
                        String phone;

                        do {
                            System.out.print("Unique 10 digit Phone #(only numbers): ");
                            phone = input.nextLine().trim();

                            if (!phone.matches("\\d{10}")) {
                                System.out.println("Invalid phone number");
                                continue;
                            } else if (phoneExists(phone)) {
                                System.out.println("Phone number already in use!");
                            }

                        } while (phoneExists(phone) || !phone.matches("\\d{10}"));

                        customer.setPhone(phone);
                        break;

                    case 4:
                        System.out.print("Unit Type: ");
                        String unitType = input.nextLine().trim();

                        customer.setUnitType(unitType);
                        break;

                    case 5:
                        LocalDate nextServiceDate;

                        do {
                            try {
                                System.out.print("Next Service Date (yyyy-MM-dd): ");
                                nextServiceDate = LocalDate.parse(input.nextLine().trim());

                                if (nextServiceDate.isBefore(LocalDate.now())) {
                                    System.out.println("Next service date is in the past");
                                }
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date format. Use yyyy-MM-dd.");
                                nextServiceDate = null;
                            }
                        } while (nextServiceDate == null || nextServiceDate.isBefore(LocalDate.now()));

                        customer.setNextServiceDate(nextServiceDate);
                        break;

                    case 6:
                        double invoiceDue;

                        do {
                            try {
                                System.out.print("Invoice Due: ");
                                invoiceDue = Double.parseDouble(input.nextLine().trim());

                                if (invoiceDue <= 0) {
                                    System.out.println("Invoice Due must be greater than zero!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid invoice due.");
                                invoiceDue = -1.0;
                            }

                        } while (invoiceDue < 0);

                        customer.setInvoiceDue(invoiceDue);
                        break;

                    case 7:
                        System.out.print("Last Service: ");
                        String lastService = input.nextLine().trim();

                        customer.setLastService(lastService);
                        break;

                    case 8:
                        LocalDate lastServiceDate;

                        do {
                            try {
                                System.out.print("Last Service Date (yyyy-MM-dd): ");
                                lastServiceDate = LocalDate.parse(input.nextLine().trim());

                                if (lastServiceDate.isAfter(LocalDate.now())) {
                                    System.out.println("Last service date cannot be in the future");
                                }
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date format. Use yyyy-MM-dd.");
                                lastServiceDate = null;
                            }

                        } while (lastServiceDate == null || lastServiceDate.isAfter(LocalDate.now()));

                        customer.setLastServiceDate(lastServiceDate);
                        break;

                    case 9:
                        double lastServiceCost;
                        do {
                            try {
                                System.out.print("Last Service Cost: ");
                                lastServiceCost = Double.parseDouble(input.nextLine().trim());

                                if (lastServiceCost <= 0) {
                                    System.out.println("Last service cost must be greater than zero!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid service cost.");
                                lastServiceCost = -1.0;
                            }

                        } while (lastServiceCost < 0);

                        customer.setLastServiceCost(lastServiceCost);
                        break;

                }
                return customer;
            }
        }
        return null;
    }

    /*
   -------------------------------------------------------------------------------------------------------------------
   Name: removeCustomer
   Purpose: Removes a customer object from the ArrayList customers.
   Arguments: A String containing a phone number.
   Returns: The removed customer object.
   Notes: Returns null if not found or removed.
    */
    public Customer removeCustomer(String phoneNumber) {
        //Checks for existing Customer-------------------------------------
        for (int i = 0; i < this.customers.size(); i++) {
            if (customers.get(i).getPhone().equals(phoneNumber)) {
                System.out.println("Customer found: " + customers.get(i).getName());
                System.out.println("Do you wish to remove this customer?");
                System.out.println("1. Yes");
                System.out.println("2. No");

                //Choice input validation-----------------------------------
                int choice = 0;

                do {

                    try {
                        System.out.println("Choice: ");
                        choice = Integer.parseInt(input.nextLine());

                        if (choice < 1 || choice > 2) {
                            System.out.println("Invalid choice!");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a number.");
                    }

                } while (choice < 1 || choice > 2);

                //Choice logic handling--------------------------------
                switch (choice) {
                    case 1:
                        return customers.remove(i);

                    case 2:
                        return null;

                }
            }
        }
        return null;
    }

    /*
   --------------------------------------------------------------------------------------
   Name: viewCustomers()
   Purpose: Fetches customer object list for display by main method.
   Arguments:
   Returns: An ArrayList containing customer objects.
   Notes: I have to admit this one felt a little redundant because main could technically just access
   customers on its own using dms.customers. However, for the purpose of this assignment I was unsure of another way to
   do this and meet the criteria.
    */
    public ArrayList<Customer> viewCustomers() {
        return customers;
    }

    /*
   --------------------------------------------------------------------------------------
   Name: displayMenu
   Purpose: Shows main menu and validates input
   Arguments:
   Returns: An integer representing a valid choice
   Notes: Is called everytime at the beginning of the main method "while loop" unless choice = 6
   Created specifically for required test case.
    */
    public int displayMenu(){
        System.out.println("\n-------Main Menu--------");
        System.out.println("1. View Customers");
        System.out.println("2. Add Customer");
        System.out.println("3. Remove Customer");
        System.out.println("4. Edit Customer");
        System.out.println("5. Check # of days since last service.");
        System.out.println("6. Exit");

        int choice = 0;

        //Choice input validation-----------------------------------
        do {

            try {
                System.out.println("Choice: ");
                choice = Integer.parseInt(input.nextLine());

                if (choice < 1 || choice > 6) {
                    System.out.println("\nInvalid choice!");
                }

            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a number.");
            }

        } while (choice < 1 || choice > 6);

        return choice;
    }

    public int checkDaysSinceLastService(String phoneNumber) {
        for (Customer customer : this.customers) {
            if (customer.getPhone().equals(phoneNumber)) {
                System.out.println("Customer found: " + customer.getName());

                int days = customer.calculateDaysSinceLastService();

                if (days < 1) {
                    return 0;
                }

                return days;

            }
        }
        return -1;
    }

    public static void main(String[] args) {
        DMS dms = new DMS();
        Scanner input = new Scanner(System.in);
        String phoneNumber = "";
        int choice = 0;
        int subChoice = 0;

        System.out.println("Welcome to AC DMS!");

        while (choice != 6) {

            choice = dms.displayMenu();

            //Handles choice logic and validation------------------------
            switch (choice) {
                case 1:
                    if (dms.viewCustomers().isEmpty()) {
                        System.out.println("\nNo customers found!");
                        break;
                    }

                    for (Customer customer : dms.viewCustomers()) {
                        System.out.println(customer);
                    }
                    break;

                case 2:
                    System.out.println("\nWould you like to add a new customer manually or with a text file?");
                    System.out.println("1. Manually");
                    System.out.println("2. Text File");

                    do {

                        try {
                            System.out.println("Choice: ");
                            subChoice = Integer.parseInt(input.nextLine());

                            if (subChoice < 1 || subChoice > 2) {
                                System.out.println("\nInvalid choice!");
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("\nPlease enter a number.");
                        }

                    } while (subChoice < 1 || subChoice > 2);

                    switch (subChoice) {
                        case 1:
                            dms.customers.add(dms.addCustomerManually());
                            break;

                        case 2:
                            System.out.println("Text file location: ");
                            String file = input.nextLine();
                            dms.customers.addAll(dms.addCustomerFromFile(file));
                            break;

                    }
                    break;

                case 3:
                    System.out.println("\nEnter phone number of customer you wish to remove: ");
                    phoneNumber = input.nextLine();
                    Customer removedCustomer = dms.removeCustomer(phoneNumber);
                    if (removedCustomer != null) {
                        System.out.println("\nCustomer removed successfully!");
                        System.out.println(removedCustomer);
                    } else {
                        System.out.println("\nNo customer removed!");
                    }
                    break;

                case 4:
                    System.out.println("\nEnter phone number of customer you wish to edit: ");
                    phoneNumber = input.nextLine();
                    Customer editedCustomer = dms.editCustomer(phoneNumber);
                    if (editedCustomer != null) {
                        System.out.println("\nCustomer was edited successfully!");
                        System.out.println(editedCustomer);
                    } else {
                        System.out.println("\nCustomer was not found!");
                    }
                    break;

                case 5:
                    System.out.println("\nEnter phone number of customer you wish to check: ");
                    phoneNumber = input.nextLine();
                    int days = dms.checkDaysSinceLastService(phoneNumber);

                    if (days== -1){
                        System.out.println("\nCustomer not found!");
                    } else{
                        System.out.println("Days since last service: " + days);
                    }


                    break;

                case 6:
                    System.out.println("Thank you for using AC DMS!");
                    break;
            }
        }
    }
}