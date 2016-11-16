package bg.elsys.ip.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bg.elsys.ip.rest.Car;
import bg.elsys.ip.rest.Color;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/colors")
@Api("colors")
public class ColorResource {
	@GET
	@ApiOperation(value = "Returns all available colors",
			response = Color.class,
			responseContainer = "List")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getColors() {
		return Response.ok(Color.values()).build();
	}
}
