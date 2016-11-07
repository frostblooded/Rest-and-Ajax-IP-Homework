package bg.elsys.ip.rest.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
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

@Path("/cars")
public class CarResource {
	public final static int ELEMENTS_PER_PAGE = 10;
	
	private Response handlePageParam(List<Car> cars, int page) {
		int bottomLimit = page * ELEMENTS_PER_PAGE;
		int upperLimit = (page + 1) * ELEMENTS_PER_PAGE;
		
		if(upperLimit > cars.size()) {
			if(bottomLimit >= cars.size())
				return Response.status(Status.NOT_FOUND).entity("{\"error\": \"page not found\"}").build();
			
			upperLimit = cars.size();
		}
		
		return Response.ok(cars.subList(bottomLimit, upperLimit)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCars(@DefaultValue("-1") @QueryParam("page") int page) {
		List<Car> cars = CarsData.getInstance().getCars();
		
		// If page parameter is passed
		if(page >= 0)
			return handlePageParam(cars, page);
		
		return Response.ok(cars).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCar(@FormParam("name") String name,
			@FormParam("color") String color) throws URISyntaxException {
		Car car = new Car(CarsData.getInstance().getNextId(), name, Color.valueOf(color));
		CarsData.getInstance().getCars().add(car);
		
		return Response.temporaryRedirect(new URI("../")).build();
	}
}
