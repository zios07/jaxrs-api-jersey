package edu.asupoly.ser422.lab3.api;

import java.util.List;

import javax.validation.Valid;
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

import edu.asupoly.ser422.lab3.model.PhoneEntry;
import edu.asupoly.ser422.lab3.service.PhoneEntryService;

@Path("phoneEntries")
public class PhoneEntryResource {

	private PhoneEntryService pEntryService = new PhoneEntryService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPhoneBookByPhoneNumber(@QueryParam("phone") String phone) {
		PhoneEntry pEntry = pEntryService.getPhoneEntryByPhoneNumber(phone);
		return Response.status(Response.Status.OK).entity(pEntry).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePhoneBook(PhoneEntry pEntry) {
		pEntry = pEntryService.createPhoneEntry(pEntry);
		return Response.status(Response.Status.OK).entity(pEntry).build();
	}

	@PUT
	@Path("/moveto/{pBookId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response movePhoneEntryToPhoneBook(@Valid PhoneEntry pEntry, @PathParam("pBookId") String pBookId) {
		boolean moved = pEntryService.movePhoneEntry(pEntry, pBookId);
		return Response.status(Response.Status.OK).entity(moved ? "Success" : "Failed").build();
	}

	@PUT
	@Path("/update-phone-number")
	public Response updatePhoneNumber(PhoneEntry pEntry) {
		pEntry = pEntryService.updatePhoneNumber(pEntry);
		return Response.status(Response.Status.OK).entity(pEntry).build();
	}
	
	@DELETE
	@Path("/{pEntryId}")
	public Response deletePhoneBook(@PathParam("pEntryId") String pEntryId) {
		boolean deleted = pEntryService.deletePhoneEntry(pEntryId);
		return Response.status(Response.Status.OK).entity(deleted).build();
	}
	
	@GET
	@Path("/search")
	public Response searchByCriteria(@QueryParam("lastname") String lastname, @QueryParam("areaCode") int areaCode) {
		List<PhoneEntry> result = pEntryService.findByCriteria(lastname, areaCode);
		return Response.status(Response.Status.OK).entity(result).build();
	}
	
}
