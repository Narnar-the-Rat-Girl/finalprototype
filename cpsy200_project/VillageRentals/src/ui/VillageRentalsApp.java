
package ui;

import data.*; 
import java.time.LocalDate;
import java.util.*;


public class VillageRentalsApp {
    private static DatabaseManager db = new DatabaseManager();
    private static List<Customer> customers;
    private static List<Equipment> inventory;
    private static List<Rental> rentals;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        customers = db.loadCustomers();
        inventory = db.loadEquipment();
        rentals = db.loadRentals();

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
                    case 6: System.out.println("Goodbye!");
                    		return;
                    		
                    default: System.out.println("Invalid option. Please choose 1-6.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
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



        int newId =  1000 + customers.size() + 1;
        

        Customer newCust = new Customer(newId, fName, lName, phone, email, false);
        customers.add(newCust);
        db.saveCustomers(customers); 
        
        System.out.println("Customer saved");
    }

    private static void addEquipment() {
        System.out.print("Enter Category ID: ");
        String categoryIdInput = scanner.nextLine();
        System.out.print("Enter Equipment Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Daily Cost: ");
        String costInput = scanner.nextLine();

       
        try {
            double cost = Double.parseDouble(costInput);
            
            if (cost < 0) {
                System.out.println("ERROR: Cost cannot be a negative number.");
                return;
            }

            // Auto-generate the next ID
            int newId = inventory.isEmpty() ? 101 : inventory.get(inventory.size() - 1).getId() + 1;
            
            // Instantiate the object and save
            Equipment newEquip = new Equipment(newId, categoryIdInput, name, cost);
            inventory.add(newEquip);
            db.saveEquipment(inventory);
            
            System.out.println("Equipment saved");
            
        } catch (NumberFormatException e) {
            System.out.println("Cost must be a valid decimal number.");
        }
    }

    private static void processRental() {
        try {
            System.out.print("Enter Customer ID: ");
            int custId = Integer.parseInt(scanner.nextLine());
            
            Customer activeCustomer = null;
            for (Customer c : customers) {
                if (c.getId() == custId) {
                    activeCustomer = c;
                    break;
                }
            }
            
            if (activeCustomer == null) {
                System.out.println("Customer ID not found.");
                return;
            }
            if (activeCustomer.isBanned()) {
                System.out.println("This customer is banned from renting equipment.");
                return;
            }

            System.out.print("enter equipment ID: ");
            int equipId = Integer.parseInt(scanner.nextLine());
            
            Equipment activeEquip = null;
            for (Equipment e : inventory) {
                if (e.getId() == equipId) {
                    activeEquip = e;
                    break;
                }
            }
            
            if (activeEquip == null) {
                System.out.println("equipment ID not found.");
                return;
            }

            System.out.print("Enter Rental Duration (in days): ");
            int days = Integer.parseInt(scanner.nextLine());

            double totalCost = activeEquip.getDailyCost() * days;
            
            int newRentId = rentals.isEmpty() ? 1000 : rentals.get(rentals.size() - 1).getRentalId() + 1;
            
            Rental newRental = new Rental(newRentId, custId, equipId, LocalDate.now(), days, totalCost);
            rentals.add(newRental);
            db.saveRentals(rentals);

            System.out.println("\n--- RENTAL RECEIPT ---");
            System.out.println(newRental.toString());
            System.out.println("rental successfully processed and saved!");

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid inputs");
        }
    }

    private static void displayCustomers() {
        System.out.println("\n--- CUSTOMER LIST ---");
        if (customers.isEmpty()) System.out.println("No customers found.");

        for (Customer c : customers) System.out.println(c.toString());
    }

    private static void displayEquipment() {
        System.out.println("\n--- EQUIPMENT INVENTORY ---");
        if (inventory.isEmpty()) System.out.println("No equipment found.");

        for (Equipment e : inventory) System.out.println(e.toString());
    }
}