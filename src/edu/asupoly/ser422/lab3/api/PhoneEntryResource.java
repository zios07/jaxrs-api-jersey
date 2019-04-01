package edu.asupoly.ser422.lab3.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.asupoly.ser422.lab3.exception.CustomException;
import edu.asupoly.ser422.lab3.model.PhoneEntry;
import edu.asupoly.ser422.lab3.service.PhoneEntryService;

@Path("phoneEntries")
@Produces(MediaType.APPLICATION_JSON)
public class PhoneEntryResource {

	private PhoneEntryService pEntryService = new PhoneEntryService();

	@GET
	public Response getPhoneBookByPhoneNumber(@QueryParam("pbookName") String pbookName,
			@QueryParam("phone") String phone) throws CustomException {
		PhoneEntry pEntry = pEntryService.getPhoneEntryByPhoneNumber(pbookName, phone);
		return Response.status(Response.Status.OK).entity(pEntry).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePhoneBook(PhoneEntry pEntry) throws CustomException {
		pEntry = pEntryService.createPhoneEntry(pEntry);
		return Response.status(Response.Status.OK).entity(pEntry).build();
	}

	@PUT
	@Path("{phoneNumber}/move-to-phone-book/{pbookName}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response movePhoneEntryToPhoneBook(@PathParam("phoneNumber") String phoneNumber,
			@PathParam("pbookName") String pbookName) throws CustomException {
		boolean moved = pEntryService.movePhoneEntry(phoneNumber, pbookName);
		return Response.status(Response.Status.OK).entity(moved ? "Success" : "Failed").build();
	}

	@PUT
	@Path("/update-phone-number")
	public Response updatePhoneNumber(@QueryParam("oldPhone") String oldPhone, @QueryParam("newPhone") String newPhone)
			throws CustomException {
		PhoneEntry pEntry = pEntryService.updatePhoneNumber(oldPhone, newPhone);
		return Response.status(Response.Status.OK).entity(pEntry).build();
	}

	@DELETE
	@Path("/{phoneNumber}")
	public Response deletePhoneBook(@PathParam("phoneNumber") String phoneNumber) throws CustomException {
		boolean deleted = pEntryService.deletePhoneEntry(phoneNumber);
		return Response.status(Response.Status.OK).entity(deleted).build();
	}

	@GET
	@Path("/search/pbook/{pbookName}")
	public Response searchByCriteria(@PathParam("pbookName") String pbookName, @QueryParam("lastname") String lastname, @QueryParam("areaCode") int areaCode) throws CustomException {
		List<PhoneEntry> result = pEntryService.findByCriteria(pbookName, lastname, areaCode);
		return Response.status(Response.Status.OK).entity(result).build();
	}

}
