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

@Path("/manufacturers")
public class ManufacturerResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getManufacturers(@QueryParam("contains") String contains) {
		List<Car> cars = CarsData.getInstance().getCars();
		
		List<String> manufacturers = cars.stream()
									     .map(c -> c.getManufacturer())
									     .distinct()
									     .collect(Collectors.toList());
		
		// If 'contains' parameter isn't passed
		if(contains == null)
			return Response.status(Status.BAD_REQUEST).entity("contains parameter is required").build();
		
		manufacturers = manufacturers.stream()
									 .filter(m -> m.toLowerCase().contains(contains.toLowerCase()))
									 .collect(Collectors.toList());
		
		return Response.ok(manufacturers).build();
	}
}
