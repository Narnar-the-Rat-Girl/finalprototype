
package data;

/**
 * The Customer model class.
 * This holds the data for a single customer in the system.
 */
public class Customer {
    // Private attributes (Encapsulation)
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private boolean isBanned;

    // Constructor to initialize a new Customer object
    public Customer(int id, String firstName, String lastName, String phone, String email, boolean isBanned) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.isBanned = isBanned;
    }

    // Getters to access private data safely
    public int getId() { return id; }
    public boolean isBanned() { return isBanned; }

    // Formats the object's data into a single string separated by semicolons
    // This makes it easy to save to our text file database.
    public String toFileFormat() {
        return id + ";" + firstName + ";" + lastName + ";" + phone + ";" + email + ";" + isBanned;
    }

    // Formats the object for nice display in the console menu
    @Override
    public String toString() {
        String status = isBanned ? "BANNED" : "Active";
        return "ID: " + id + " | " + firstName + " " + lastName + " | Phone: " + phone + " | Status: " + status;
    }
}