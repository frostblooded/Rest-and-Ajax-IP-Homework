package bg.elsys.ip.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/cars")
public class CarResource {
	public final static int ELEMENTS_PER_PAGE = 10;
	
	private Response handlePageParam(List<Car> cars, int page) {
		int bottomLimit = page * ELEMENTS_PER_PAGE;
		int upperLimit = (page + 1) * ELEMENTS_PER_PAGE;
		
		if(upperLimit > cars.size()) {
			if(bottomLimit > cars.size())
				return Response.status(Status.NOT_FOUND).build();
			
			upperLimit = cars.size();
		}
		
		return Response.ok(cars.subList(bottomLimit, upperLimit)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@DefaultValue("-1") @QueryParam("page") int page) {
		List<Car> cars = new ArrayList<>();
		
		for(int i = 0; i < 100; i++)
			cars.add(new Car(i, "Car" + String.valueOf(i), "black"));
		
		// If page parameter is passed
		if(page >= 0)
			return handlePageParam(cars, page);
		
		return Response.ok(cars).build();
	}
}
