package bg.elsys.ip.rest.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import bg.elsys.ip.rest.Car;
import bg.elsys.ip.rest.CarsData;

@Path("/years")
public class YearResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getYears(@QueryParam("contains") String contains) {
		List<Car> cars = CarsData.getInstance().getCars();
		
		List<String> years = cars.stream()
							     .map(c -> c.getYear())
							     .distinct()
							     .collect(Collectors.toList());
		
		// If 'contains' parameter isn't passed
		if(contains == null)
			return Response.status(Status.BAD_REQUEST).entity("contains parameter is required").build();
		
		years = years.stream()
		 			 .filter(y -> y.toLowerCase().contains(contains.toLowerCase()))
		 			 .collect(Collectors.toList());
		
		
		return Response.ok(years).build();
	}
}
