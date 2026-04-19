package gui;

import classes.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class GUI {
    private static List<CustomerInformation> customers = new ArrayList<>();
    private static List<RentalEquipment> inventory = new ArrayList<>();
    private static List<RentalInformation> rentals = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    // File paths
    private static final String CUST_FILE = "customers.txt";
    private static final String EQUIP_FILE = "equipment.txt";
    private static final String RENT_FILE = "rentals.txt";

    public static void main(String[] args) {
        loadAllData();

        int choice = 0;
        while (choice != 6) {
            System.out.println("\n=== VILLAGE RENTALS SYSTEM ===");
            System.out.println("1. Add New Customer");
            System.out.println("2. Add New Equipment");
            System.out.println("3. View All Customers");
            System.out.println("4. View All Equipment");
            System.out.println("5. Process a Rental");
            System.out.println("6. Exit");
            System.out.print("Enter option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: addCustomer(); break;
                    case 2: addEquipment(); break;
                    case 3: displayCustomers(); break;
                    case 4: displayEquipment(); break;
                    case 5: processRental(); break;
                    case 6: System.out.println("Goodbye!"); break;
                    default: System.out.println("Invalid choose 1-6.");
                }
            } catch (Exception e) {
                System.out.println(" enter a number.");
            }
        }
    }

    private static void addCustomer() {
        System.out.print("Enter First Name: ");
        String fName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lName = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        // VALIDATION: Ensure names are not empty
        if (fName.trim().isEmpty() || lName.trim().isEmpty()) {
            System.out.println("First and Last name need input");
            return;
        }

        // VALIDATION: Ensure names don't contain numbers
        if (fName.matches(".*\\d.*") || lName.matches(".*\\d.*")) {
            System.out.println("names can only be letters");
            return;
        }

        int newId = customers.isEmpty() ? 1001 : customers.get(customers.size() - 1).getCustomerid() + 1;
        CustomerInformation newCust = new CustomerInformation(newId, lName, fName, phone, email);
        
        customers.add(newCust);
        saveCustomers();
        System.out.println("Customer saved");
    }

    private static void addEquipment() {
        try {
            System.out.print("Enter Category ID: ");
            int categoryId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Equipment Name: ");
            String name = scanner.nextLine();
            
            // check if name is ok
            if (name.trim().isEmpty()) {
                System.out.println("Equipment name cannot be empty.");
                return;
            }
            if (name.matches("^\\d+$")) {
                System.out.println("Equipment needs to be letters");
                return;
            }

            System.out.print("Enter Description: ");
            String desc = scanner.nextLine();
            
            System.out.print("Enter Daily Rate: ");
            double rate = Double.parseDouble(scanner.nextLine());

            // make sure the rate is higher then 0
            if (rate < 0) {
                System.out.println("Rate must be positve");
                return;
            }

            int newId = inventory.isEmpty() ? 101 : inventory.get(inventory.size() - 1).getEquipmentid() + 1;
            RentalEquipment newEquip = new RentalEquipment(newId, categoryId, name, desc, rate);
                
            inventory.add(newEquip);
            saveEquipment();
            System.out.println("Success! Equipment saved.");
            
        } catch (NumberFormatException e) {
            System.out.println("Category ID and Daily Rate must be valid numbers.");
        }
    }

    private static void processRental() {
        try {
            System.out.print("Enter Customer ID: ");
            int custId = Integer.parseInt(scanner.nextLine());
            
            // see if customer is real
            boolean customerExists = false;
            for (CustomerInformation c : customers) {
                if (c.getCustomerid() == custId) {
                    customerExists = true;
                    
                }
            }
            if (!customerExists) {
                System.out.println("Customer ID not found");
                return;
            }
            
            System.out.print("Enter Equipment ID: ");
            int equipId = Integer.parseInt(scanner.nextLine());

            // see if equpment is real
            RentalEquipment activeEquip = null;
            for (RentalEquipment e : inventory) {
                if (e.getEquipmentid() == equipId) {
                    activeEquip = e;
                    break;
                }
            }
            
            if (activeEquip == null) {
                System.out.println("Equipment ID not found.");
                return;
            }

            System.out.print("Enter Rental Duration (days): ");
            int days = Integer.parseInt(scanner.nextLine());
            
//            must rent for one or more day
            if (days <= 0) {
                System.out.println("Duration must be at least 1 day.");
                return;
            }
            
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, days);
            Date returnDate = cal.getTime();

            double costCalc = activeEquip.getDailyrate() * days;
            int totalCost = (int) Math.round(costCalc);
            
            int newRentId = rentals.isEmpty() ? 1000 : rentals.get(rentals.size() - 1).getRentalid() + 1;
                
            RentalInformation newRental = new RentalInformation(newRentId, today, custId, equipId, today, returnDate, totalCost);
            rentals.add(newRental);
            saveRentals();

            System.out.println("\n--- RENTAL RECEIPT ---");
            System.out.println(newRental.toString());
            
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers for IDs and Days.");
        }
    }

    private static void displayCustomers() {
        System.out.println("\n--- CUSTOMER LIST ---");
        if (customers.isEmpty()) System.out.println("No customers found.");
        for (CustomerInformation c : customers) System.out.println(c.toString());
    }

    private static void displayEquipment() {
        System.out.println("\n--- EQUIPMENT INVENTORY ---");
        if (inventory.isEmpty()) System.out.println("No equipment found.");
        for (RentalEquipment e : inventory) System.out.println(e.toString());
    }

    private static void loadAllData() {
        try (Scanner sc = new Scanner(new File(CUST_FILE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] p = line.split(";");
                    customers.add(new CustomerInformation(Integer.parseInt(p[0]), p[1], p[2], p[3], p[4]));
                }
            }
        } catch (Exception e) {}

        try (Scanner sc = new Scanner(new File(EQUIP_FILE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] p = line.split(";");
                    inventory.add(new RentalEquipment(Integer.parseInt(p[0]), Integer.parseInt(p[1]), p[2], p[3], Double.parseDouble(p[4])));
                }
            }
        } catch (Exception e) {}

        try (Scanner sc = new Scanner(new File(RENT_FILE))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] p = line.split(";");
                    rentals.add(new RentalInformation(Integer.parseInt(p[0]), sdf.parse(p[1]), 
                             Integer.parseInt(p[2]), Integer.parseInt(p[3]), 
                             sdf.parse(p[4]), sdf.parse(p[5]), 
                             Integer.parseInt(p[6])));
                }
            }
        } catch (Exception e) {}
    }
// saves to file
    private static void saveCustomers() {
        try (PrintWriter writer = new PrintWriter(new File(CUST_FILE))) {
            for (CustomerInformation c : customers) writer.println(c.toFileFormat());
        } catch (Exception e) {}
    }

    private static void saveEquipment() {
        try (PrintWriter writer = new PrintWriter(new File(EQUIP_FILE))) {
            for (RentalEquipment e : inventory) writer.println(e.toFileFormat());
        } catch (Exception e) {}
    }

    private static void saveRentals() {
        try (PrintWriter writer = new PrintWriter(new File(RENT_FILE))) {
            for (RentalInformation r : rentals) writer.println(r.toFileFormat());
        } catch (Exception e) {}
    }
}