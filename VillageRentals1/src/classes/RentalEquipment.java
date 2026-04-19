package classes;

public class RentalEquipment {
    private int equipmentid;
    private int categoryid;
    private String name;
    private String description;
    private double dailyrate; 

    public RentalEquipment(int equipmentid, int categoryid, String name, String description, double dailyrate) {
        this.equipmentid = equipmentid;
        this.categoryid = categoryid;
        this.name = name;
        this.description = description;
        this.dailyrate = dailyrate;
    }

    public int getEquipmentid() { return equipmentid; }
    public double getDailyrate() { return dailyrate; }

    public String toFileFormat() {
        return equipmentid + ";" + categoryid + ";" + name + ";" + description + ";" + dailyrate;
    }

    @Override
    public String toString() {
        return "ID: " + equipmentid + " | Name: " + name + " | Rate: $" + dailyrate + "/day | Desc: " + description;
    }
}