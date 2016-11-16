package bg.elsys.ip.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bg.elsys.ip.rest.Color;

@Path("/colors")
public class ColorResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getColors() {
		return Response.ok(Color.values()).build();
	}
}
