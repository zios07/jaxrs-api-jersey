package edu.asupoly.ser422.lab3.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.asupoly.ser422.lab3.exception.CustomException;
import edu.asupoly.ser422.lab3.model.PhoneBook;
import edu.asupoly.ser422.lab3.service.PhoneBookService;

@Path("/phonebooks")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class PhoneBookResource {

	private PhoneBookService pbookService = new PhoneBookService();

	@GET
	@Path("/{pbookName}")
	public Response getPhoneBookByName(@PathParam("pbookName") String pbookName) throws CustomException {
		PhoneBook pbook = pbookService.getPhoneBook(pbookName);
		return Response.status(Response.Status.OK).entity(pbook.get_pbook().values()).build();
	}

	@DELETE
	@Path("/{pbookName}")
	public Response deletePhoneBook(@PathParam("pbookName") String pbookName) throws CustomException {
		PhoneBook pbook = pbookService.deletePhoneBook(pbookName);
		return Response.status(Response.Status.OK).entity(pbook).build();
	}

}
