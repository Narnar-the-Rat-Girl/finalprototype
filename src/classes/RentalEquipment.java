package classes;

public class RentalEquipment {
	int Equipment_id;
	int category_id;
	String name;
	String description;
	int daily_rate;
	
	public RentalEquipment(int equipment_id, int category_id, String name, String description, int daily_rate) {
		super();
		Equipment_id = equipment_id;
		this.category_id = category_id;
		this.name = name;
		this.description = description;
		this.daily_rate = daily_rate;
	}

}
