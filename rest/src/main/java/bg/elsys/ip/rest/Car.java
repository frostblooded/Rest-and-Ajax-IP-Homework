package bg.elsys.ip.rest;

public class Car {
	private int id;
	private String manufacturer;
	private String model;
	private String year;
	private Color color;

	public Car(int id, String manufacturer, String model, String year, Color color) {
		this.id = id;
		this.manufacturer = manufacturer;
		this.model = model;
		this.year = year;
		this.color = color;
	}

	public Car(String manufacturer, String model, String year, Color color) {
		this(CarsData.getInstance().getNextId(), manufacturer, model, year, color);
	}
	
	public Car() {
		this("", "", "", null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
