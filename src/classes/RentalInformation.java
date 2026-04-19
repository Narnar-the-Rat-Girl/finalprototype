package classes;

import java.util.Date;

public class RentalInformation {
	int rental_id;
	Date date;
	int customer_id;
	int equipment_id;
	Date rental_date;
	Date return_date;
	int cost;
	
	public RentalInformation(int rental_id, Date date, int customer_id, int equipment_id, Date rental_date,
			Date return_date, int cost) {
		super();
		this.rental_id = rental_id;
		this.date = date;
		this.customer_id = customer_id;
		this.equipment_id = equipment_id;
		this.rental_date = rental_date;
		this.return_date = return_date;
		this.cost = cost;
	}

}
