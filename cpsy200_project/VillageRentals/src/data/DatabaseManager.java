package data;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * DatabaseManager handles all reading and writing to text files.
 * This demonstrates "Separation of Concerns" (keeping data logic out of the UI).
 */
public class DatabaseManager {
    // File names for our text-based database
    private final String CUST_FILE = "customers.txt";
    private final String EQUIP_FILE = "equipment.txt";
    private final String RENT_FILE = "rentals.txt";

    // --- CUSTOMER METHODS ---
    
    // Saves the entire list of customers by overwriting the text file
    public void saveCustomers(List<Customer> customers) {
        try (PrintWriter writer = new PrintWriter(new File(CUST_FILE))) {
            for (Customer c : customers) {
                writer.println(c.toFileFormat()); // Writes the semicolon separated string
            }
        } catch (Exception e) {
            System.out.println("Error saving customers to database.");
        }
    }

    // Reads the text file line-by-line, splits the text, and recreates the Customer objects
    public List<Customer> loadCustomers() {
        List<Customer> list = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(CUST_FILE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty()) continue;
                
                String[] p = line.split(";"); // Split at the semicolon
                // Parse the strings back into proper data types
                list.add(new Customer(Integer.parseInt(p[0]), p[1], p[2], p[3], p[4], Boolean.parseBoolean(p[5])));
            }
        } catch (Exception e) {
            // File might not exist yet, which is fine. It will return an empty list.
        }
        return list;
    }

    // --- EQUIPMENT METHODS ---
    
    public void saveEquipment(List<Equipment> equipmentList) {
        try (PrintWriter writer = new PrintWriter(new File(EQUIP_FILE))) {
            for (Equipment e : equipmentList) {
                writer.println(e.toFileFormat());
            }
        } catch (Exception e) {
            System.out.println("Error saving equipment to database.");
        }
    }

    public List<Equipment> loadEquipment() {
        List<Equipment> list = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(EQUIP_FILE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                list.add(new Equipment(Integer.parseInt(p[0]), p[1], p[2], Double.parseDouble(p[3])));
            }
        } catch (Exception e) {}
        return list;
    }

    // --- RENTAL METHODS ---

    public void saveRentals(List<Rental> rentals) {
        try (PrintWriter writer = new PrintWriter(new File(RENT_FILE))) {
            for (Rental r : rentals) {
                writer.println(r.toFileFormat());
            }
        } catch (Exception e) {
            System.out.println("Error saving rentals to database.");
        }
    }

    public List<Rental> loadRentals() {
        List<Rental> list = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(RENT_FILE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                list.add(new Rental(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]), LocalDate.parse(p[3]), Integer.parseInt(p[4]), Double.parseDouble(p[5])));
            }
        } catch (Exception e) {}
        return list;
    }
}