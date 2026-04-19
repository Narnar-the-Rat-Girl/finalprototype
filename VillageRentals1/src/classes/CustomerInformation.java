package classes;

public class CustomerInformation {
    private int customerid;
    private String lastname;
    private String firstname;
    private String contactphone;
    private String email;

    public CustomerInformation(int customerid, String lastname, String firstname, String contactphone, String email) {
        this.customerid = customerid;
        this.lastname = lastname;
        this.firstname = firstname;
        this.contactphone = contactphone;
        this.email = email;
    }

    public int getCustomerid() { return customerid; }

    public String toFileFormat() {
        return customerid + ";" + lastname + ";" + firstname + ";" + contactphone + ";" + email;
    }

    @Override
    public String toString() {
        return "ID: " + customerid + " | " + firstname + " " + lastname + " | Phone: " + contactphone;
    }
}