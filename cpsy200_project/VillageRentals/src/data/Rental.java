
package data;

import java.time.LocalDate;

/**
 * The Rental model class.
 * This links a Customer to a piece of Equipment and tracks the cost.
 */
public class Rental {
    // Private attributes
    private int rentalId;
    private int customerId;
    private int equipmentId;
    private LocalDate rentalDate;
    private int durationDays;
    private double totalCost;

    // Constructor to initialize a new Rental transaction
    public Rental(int rentalId, int customerId, int equipmentId, LocalDate rentalDate, int durationDays, double totalCost) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.equipmentId = equipmentId;
        this.rentalDate = rentalDate;
        this.durationDays = durationDays;
        this.totalCost = totalCost;
    }

    public int getRentalId() { return rentalId; }

    // Prepares the string for saving to rentals.txt
    public String toFileFormat() {
        return rentalId + ";" + customerId + ";" + equipmentId + ";" + rentalDate + ";" + durationDays + ";" + totalCost;
    }

    // Prepares the receipt for the console UI display
    @Override
    public String toString() {
        return "Rental ID: " + rentalId + " | Customer ID: " + customerId + 
               " | Equipment ID: " + equipmentId + " | Days: " + durationDays + 
               " | Total Cost: $" + String.format("%.2f", totalCost);
    }
}