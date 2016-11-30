package bg.elsys.ip.rest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@ApiModel(value = "Car", description = "A model representing a single car")
public class Car {
	@Id
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

	@ApiModelProperty(value = "car id", required = true)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ApiModelProperty(value = "car model", required = true)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@ApiModelProperty(value = "car manufacturer", required = true)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@ApiModelProperty(value = "car year", required = true)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Enumerated(EnumType.ORDINAL)
	@ApiModelProperty(value = "car color", required = true)
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
