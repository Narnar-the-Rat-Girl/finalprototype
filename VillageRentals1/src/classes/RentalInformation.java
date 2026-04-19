package classes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RentalInformation {
    private int rentalid;
    private Date date;
    private int customerid;
    private int equipmentid;
    private Date rentaldate;
    private Date returndate;
    private int cost;

    public RentalInformation(int rentalid, Date date, int customerid, int equipmentid, Date rentaldate, Date returndate, int cost) {
        this.rentalid = rentalid;
        this.date = date;
        this.customerid = customerid;
        this.equipmentid = equipmentid;
        this.rentaldate = rentaldate;
        this.returndate = returndate;
        this.cost = cost;
    }

    public int getRentalid() { return rentalid; }

    public String toFileFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return rentalid + ";" + sdf.format(date) + ";" + customerid + ";" + equipmentid + ";" + 
               sdf.format(rentaldate) + ";" + sdf.format(returndate) + ";" + cost;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Rental ID: " + rentalid + " | Date: " + sdf.format(date) + 
               " | Cust ID: " + customerid + " | Equip ID: " + equipmentid + 
               " | Return: " + sdf.format(returndate) + " | Total Cost: $" + cost;
    }
}