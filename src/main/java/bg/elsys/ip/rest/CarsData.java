package bg.elsys.ip.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarsData {
	private static final CarsData INSTANCE = new CarsData();
	private static final int SEED_SIZE = 1000;
	private List<Car> cars = new ArrayList<>();

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public int getNextId() {
		int max = -1;
		
		for(Car car: getCars()) {
			if(car.getId() > max)
				max = car.getId();
		}
		
		return max + 1;
	}
	
	public Color getRandomColor() {
		Color[] values = Color.values();
		int randomIndex = new Random().nextInt(values.length);
		return values[randomIndex];
	}

	private void generateSeedData() {
		String[] manufcaturers = new String[]{ "Audi", "Tesla", "Mercedes-Benz", "Jeep" };
		Random rand = new Random();
		
		for (int i = 0; i < SEED_SIZE; i++) {
			int id = getNextId();
			String manufacturer = manufcaturers[rand.nextInt(manufcaturers.length)];
			String model = "Model " + (char)('A' + rand.nextInt('Z' - 'A'));
			
			// Get a random year between 1950 and 2020
			String year = String.valueOf(1950 + rand.nextInt(70));
			
			cars.add(new Car(id, manufacturer, model, year, getRandomColor()));
		}
	}

	private CarsData() {
		generateSeedData();
	}

	public static CarsData getInstance() {
		return INSTANCE;
	}
}