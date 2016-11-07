package bg.elsys.ip.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarsData {
	private static final CarsData INSTANCE = new CarsData();
	private static final int SEED_SIZE = 20;
	private List<Car> cars = new ArrayList<>();

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public int getLastId() {
		int max = -1;
		
		for(Car car: cars)
			if(car.id > max)
				max = car.id;
		
		return max;
	}
	
	public Color getRandomColor() {
		Color[] values = Color.values();
		int randomIndex = new Random().nextInt(values.length);
		return values[randomIndex];
	}

	private void generateSeedData() {
		for (int i = 0; i < SEED_SIZE; i++) {
			int id = getLastId() + 1;
			String name = "Car " + id;
			cars.add(new Car(id, name, getRandomColor()));
		}
	}

	private CarsData() {
		generateSeedData();
	}

	public static CarsData getInstance() {
		return INSTANCE;
	}
}