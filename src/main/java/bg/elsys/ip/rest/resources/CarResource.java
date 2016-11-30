package bg.elsys.ip.rest.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bg.elsys.ip.rest.Car;
import bg.elsys.ip.rest.CarsData;
import bg.elsys.ip.rest.Color;
import bg.elsys.ip.rest.services.CarService;
import io.swagger.annotations.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@Path("/cars")
@Api("cars")
public class CarResource {
	private Response handlePageParam(List<Car> cars, int page, int perPage) {
		int bottomLimit = page * perPage;
		int upperLimit = (page + 1) * perPage;
		
		if(upperLimit > cars.size()) {
			if(bottomLimit >= cars.size())
				return Response.status(Status.NOT_FOUND).entity("{\"error\": \"page not found\"}").build();
			
			upperLimit = cars.size();
		}
		
		return Response.ok(cars.subList(bottomLimit, upperLimit)).build();
	}
	
	@GET
	@ApiOperation(value = "Returns cars from the list",
			response = Car.class,
			responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 404, message = "page not found")
	})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCars(@ApiParam("which part of the data should be returned") @DefaultValue("-1") @QueryParam("page") int page,
							@ApiParam("how many elements will be returned per page") @DefaultValue("10") @QueryParam("perPage") int perPage,
							@ApiParam("which color should the returned cars have") @QueryParam("color") String color,
							@ApiParam("which manufacturer should the returned cars have") @QueryParam("manufacturer") String manufacturer,
							@ApiParam("which model should the returned cars have") @QueryParam("model") String model,
							@ApiParam("which year should the returned cars have") @QueryParam("year") String year) {
//		List<Car> cars = CarsData.getInstance().getCars();
		List<Car> cars = CarService.getAll();

		// If color parameter is passed
		if(color != null) {
			cars = cars.stream()
					   .filter(c -> c.getColor() == Color.valueOf(color))
					   .collect(Collectors.toList());
		}

		// If manufacturer parameter is passed
		if(manufacturer != null) {
			cars = cars.stream()
					   .filter(c -> c.getManufacturer().toLowerCase().equals(manufacturer.toLowerCase()))
					   .collect(Collectors.toList());
		}

		// If model parameter is passed
		if(model != null) {
			cars = cars.stream()
					   .filter(c -> c.getModel().toLowerCase().equals(model.toLowerCase()))
					   .collect(Collectors.toList());
		}

		// If year parameter is passed
		if(year != null) {
			cars = cars.stream()
					   .filter(c -> c.getYear().equals(year))
					   .collect(Collectors.toList());
		}

		// If page parameter is passed
		if(page >= 0)
			return handlePageParam(cars, page, perPage);

		return Response.ok(cars).build();
	}
	
	@POST
	@ApiOperation("Creates a new car")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCar(Car car) {
		CarsData.getInstance().getCars().add(car);
		return Response.ok().build();
	}
}
