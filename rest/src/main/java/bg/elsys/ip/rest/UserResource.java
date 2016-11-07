package bg.elsys.ip.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		List<User> users = new ArrayList<>();
		users.add(new User(1, "asdf"));
		return Response.ok(users).build();
	}
}
