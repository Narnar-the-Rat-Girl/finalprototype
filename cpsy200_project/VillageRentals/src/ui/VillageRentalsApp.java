
package ui;

import data.*; // This imports Customer, Equipment, Rental, and DatabaseManager from the data package
import java.time.LocalDate;
import java.util.*;

/**
 * The main User Interface class.
 * This runs the console menu and processes user input.
 */
public class VillageRentalsApp {
    // Class-level variables
    private static DatabaseManager db = new DatabaseManager();
    private static List<Customer> customers;
    private static List<Equipment> inventory;
    private static List<Rental> rentals;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load existing data from text files at startup
        customers = db.loadCustomers();
        inventory = db.loadEquipment();
        rentals = db.loadRentals();

        int choice = 0;
        
        // Main Application Loop
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
                choice = Integer.parseInt(scanner.nextLine()); // Read input safely
                
                // Route user to the correct method
                switch (choice) {
                    case 1: addCustomer(); break;
                    case 2: addEquipment(); break;
                    case 3: displayCustomers(); break;
                    case 4: displayEquipment(); break;
                    case 5: processRental(); break;
                    case 6: System.out.println("Goodbye!"); break;
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

        // VALIDATION 1: Ensure required fields are not empty
        if (fName.trim().isEmpty() || lName.trim().isEmpty()) {
            System.out.println("ERROR: First and Last name cannot be empty. Please try again.");
            return; // Stops method and goes back to menu
        }

        // VALIDATION 2: check - Names can only contain letters, spaces, hyphens, and apostrophes
        if (!fName.matches("^[a-zA-Z\\s\\-']+$") || !lName.matches("^[a-zA-Z\\s\\-']+$")) {
            System.out.println("ERROR: Names cannot contain numbers or special characters.");
            return;
        }

        // Auto-generate the next ID
        int newId = customers.isEmpty() ? 1001 : customers.get(customers.size() - 1).getId() + 1;
        
        // Instantiate the object and save
        Customer newCust = new Customer(newId, fName, lName, phone, email, false);
        customers.add(newCust);
        db.saveCustomers(customers); // Update the text file
        
        System.out.println("Success! Customer saved to database.");
    }

    private static void addEquipment() {
        System.out.print("Enter Category ID (e.g., 10 for Power tools, 20 for Yard equipment): ");
        String categoryIdInput = scanner.nextLine();
        System.out.print("Enter Equipment Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Daily Cost ($): ");
        String costInput = scanner.nextLine();

        // VALIDATION 1: Ensure required fields are not empty
        if (categoryIdInput.trim().isEmpty() || name.trim().isEmpty() || costInput.trim().isEmpty()) {
            System.out.println("ERROR: Fields cannot be empty. Please try again.");
            return;
        }
        
        // VALIDATION 2: check - Category ID must be numbers only
        if (!categoryIdInput.matches("^\\d+$")) {
            System.out.println("ERROR: Category ID must be a number (e.g., 10, 20).");
            return;
        }
        
        // VALIDATION 3: check - Name cannot be ONLY numbers
        if (name.matches("^\\d+$")) {
            System.out.println("ERROR: Equipment name must contain letters.");
            return;
        }

        try {
            double cost = Double.parseDouble(costInput);
            
            // VALIDATION 4: Logical check - Cost cannot be negative
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
            
            System.out.println("Success! Equipment saved to database.");
            
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Cost must be a valid decimal number.");
        }
    }

    private static void processRental() {
        try {
            System.out.print("Enter Customer ID: ");
            int custId = Integer.parseInt(scanner.nextLine());
            
            // Search the array list for a matching Customer ID
            Customer activeCustomer = null;
            for (Customer c : customers) {
                if (c.getId() == custId) {
                    activeCustomer = c;
                    break;
                }
            }
            
            // Validate Customer exists and is not banned
            if (activeCustomer == null) {
                System.out.println("ERROR: Customer ID not found.");
                return;
            }
            if (activeCustomer.isBanned()) {
                System.out.println("ERROR: This customer is banned from renting equipment.");
                return;
            }

            System.out.print("Enter Equipment ID: ");
            int equipId = Integer.parseInt(scanner.nextLine());
            
            // Search the array list for a matching Equipment ID
            Equipment activeEquip = null;
            for (Equipment e : inventory) {
                if (e.getId() == equipId) {
                    activeEquip = e;
                    break;
                }
            }
            
            // Validate Equipment exists
            if (activeEquip == null) {
                System.out.println("ERROR: Equipment ID not found.");
                return;
            }

            System.out.print("Enter Rental Duration (in days): ");
            int days = Integer.parseInt(scanner.nextLine());
            if (days <= 0) {
                System.out.println("ERROR: Duration must be at least 1 day.");
                return;
            }

            // Calculate business logic (Cost = daily rate * days)
            double totalCost = activeEquip.getDailyCost() * days;
            
            // Auto-generate rental ID
            int newRentId = rentals.isEmpty() ? 1000 : rentals.get(rentals.size() - 1).getRentalId() + 1;
            
            // Instantiate Rental object and save to text file
            Rental newRental = new Rental(newRentId, custId, equipId, LocalDate.now(), days, totalCost);
            rentals.add(newRental);
            db.saveRentals(rentals);

            // Display Receipt
            System.out.println("\n--- RENTAL RECEIPT ---");
            System.out.println(newRental.toString());
            System.out.println("Rental successfully processed and saved!");

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Please enter valid numbers for IDs and Days.");
        }
    }

    private static void displayCustomers() {
        System.out.println("\n--- CUSTOMER LIST ---");
        if (customers.isEmpty()) System.out.println("No customers found.");
        // Enhanced for-loop to iterate through the collection
        for (Customer c : customers) System.out.println(c.toString());
    }

    private static void displayEquipment() {
        System.out.println("\n--- EQUIPMENT INVENTORY ---");
        if (inventory.isEmpty()) System.out.println("No equipment found.");
        // Enhanced for-loop to iterate through the collection
        for (Equipment e : inventory) System.out.println(e.toString());
    }
}