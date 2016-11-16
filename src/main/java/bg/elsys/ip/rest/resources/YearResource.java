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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/years")
@Api("years")
public class YearResource {
	@GET
	@ApiOperation(value = "Returns all unique years from the cars list",
			notes = "The years are sorted in ascending order",
			response = String.class,
			responseContainer = "List")
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
		 			 .filter(y -> y.contains(contains))
		 			 .collect(Collectors.toList());
		
		
		return Response.ok(years).build();
	}
}
