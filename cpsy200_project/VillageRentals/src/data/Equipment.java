
package data;

/**
 * The Equipment model class.
 * This holds the data for a single piece of rental equipment.
 */
public class Equipment {
    // Private attributes (Encapsulation)
    private int id;
    private String category;
    private String name;
    private double dailyCost;

    // Constructor to initialize a new Equipment object
    public Equipment(int id, String category, String name, double dailyCost) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.dailyCost = dailyCost;
    }

    // Getters to access private data
    public int getId() { return id; }
    public double getDailyCost() { return dailyCost; }

    // Prepares the string for saving to the text file
    public String toFileFormat() {
        return id + ";" + category + ";" + name + ";" + dailyCost;
    }

    // Prepares the string for the console UI display
    @Override
    public String toString() {
        return "ID: " + id + " | Category: " + category + " | Name: " + name + " | Cost: $" + dailyCost + "/day";
    }
}