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

	/**
	 * @api {get} /phonebooks/:pbookName Request PhoneBook by its name
	 * @apiName GetPhoneBookByName
	 * @apiGroup PhoneBook
	 *
	 * @apiParam {String} pbookName PhoneBook name.
	 *
	 * @apiSuccess {String} Phone entries contained in the phonebook.
	 *
	 * @apiSuccessExample Success-Response: HTTP/1.1 200 OK 
	 * 					[ { "firstname":
	 *                    "Sedona", "lastname": "Slastnamedona", "phone":
	 *                    "111-222-999" }, { "firstname": "Flagstaff", "lastname":
	 *                    "Flagstaff", "phone": "111-222-777" } ]
	 *
	 * @apiError PhoneBookNotFound The PhoneBook was not found.
	 *
	 * @apiErrorExample Error-Response: HTTP/1.1 404 Not Found 
	 * 					{ "errorMessage":
	 *                  "File with name : pb13 does not exist!", "statusCode": 404,
	 *                  "statusDescription": "Not Found" }
	 */

	@GET
	@Path("/{pbookName}")
	public Response getPhoneBookByName(@PathParam("pbookName") String pbookName) throws CustomException {
		PhoneBook pbook = pbookService.getPhoneBook(pbookName);
		return Response.status(Response.Status.OK).entity(pbook.get_pbook().values()).build();
	}

	/**
	 * @api {delete} /phonebooks/:pbookName Delete PhoneBook by its name
	 * @apiName DeletePhoneBookByName
	 * @apiGroup PhoneBook
	 *
	 * @apiParam {String} pbookName PhoneBook name.
	 *
	 * @apiSuccess {String} void
	 *
	 * @apiSuccessExample Success-Response: HTTP/1.1 200 OK 
	 * 
	 * {}
	 *
	 * @apiError PhoneBookNotFound The PhoneBook was not found.
	 *
	 * @apiErrorExample Error-Response: HTTP/1.1 404 Not Found 
	 * 					{ "errorMessage":
	 *                  "File with name : pb13 does not exist!", "statusCode": 404,
	 *                  "statusDescription": "Not Found" }
	 *                  
	 * @apiError PhoneBookNotFound The PhoneBook contains entries, cannot be deleted.
	 *
	 * @apiErrorExample Error-Response: HTTP/1.1 400 Bad Request 
	 * 					{ "errorMessage":
	 *                  "File contains entries, it cannot be deleted", "statusCode":
	 *                  400, "statusDescription": "Bad Request" }
	 *                  
	 */

	@DELETE
	@Path("/{pbookName}")
	public Response deletePhoneBook(@PathParam("pbookName") String pbookName) throws CustomException {
		PhoneBook pbook = pbookService.deletePhoneBook(pbookName);
		return Response.status(Response.Status.OK).entity(pbook).build();
	}

}
