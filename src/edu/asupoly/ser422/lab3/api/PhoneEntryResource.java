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
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class PhoneEntryResource {

	private PhoneEntryService pEntryService = new PhoneEntryService();

	/**
	 * @api {get} /phonebooks?:pbookName=val&phone=val Request PhoneEntry by its name
	 * @apiName getPhoneEntryByPhoneNumber
	 * @apiGroup PhoneEntry
	 *
	 * @apiParam {String} pbookName PhoneBook name.
	 * @apiParam {String} phone PhoneEntry phone number.
	 *
	 * @apiSuccess {String} firstname First name.
	 * @apiSuccess {String} lastname Last name.
	 * @apiSuccess {String} phone Phone number.
	 *
	 * @apiSuccessExample Success-Response: HTTP/1.1 200 OK 
	 * 					{ "firstname": "Mesa",
	 *                    "lastname": "Mlastnamesa", "phone": "111-222-111" }
	 *
	 * @apiError PhoneEntryNotFound The PhoneEntry was not found.
	 *
	 * @apiErrorExample Error-Response: HTTP/1.1 404 Not Found 
	 * 					{ "errorMessage": "No
	 *                  phone entry found for this phone number", "statusCode": 404,
	 *                  "statusDescription": "Not Found" }
	 * 
	 * @apiError PhoneBookNotFound The PhoneBook was not found.
	 * 
	 * @apiErrorExample Error-Response: HTTP/1.1 404 Not Found
	 * 
	 *                  { "errorMessage": "File with name : pb3 does not exist!",
	 *                  "statusCode": 404, "statusDescription": "Not Found" }
	 */
	@GET
	public Response getPhoneEntryByPhoneNumber(@QueryParam("pbookName") String pbookName,
			@QueryParam("phone") String phone) throws CustomException {
		PhoneEntry pEntry = pEntryService.getPhoneEntryByPhoneNumber(pbookName, phone);
		return Response.status(Response.Status.OK).entity(pEntry).build();
	}

	/**
	 * @api {post} /phoneEntries Create phone entry
	 * @apiName createPhoneEntry
	 * @apiGroup PhoneEntry
	 *
	 * @apiParam {Object} pEntry PhoneEntry object
	 * @apiParamExample {json} Request-Example: 
	 * 					{ "firstname": "DEMO", "lastname":
	 *                  "Demo", "phone": "111-222-111" }
	 *
	 *
	 * @apiSuccess {String} firstname First name.
	 * @apiSuccess {String} lastname Last name.
	 * @apiSuccess {String} phone Phone number.
	 *
	 * @apiSuccessExample Success-Response: HTTP/1.1 200 OK 
	 * 						{ "firstname": "Mesa",
	 *                    "lastname": "Mlastnamesa", "phone": "111-222-111" }
	 *
	 * @apiError PhoneNumberAlreadyExistError The phone number should be unique.
	 * 
	 * @apiErrorExample Error-Response: HTTP/1.1 400 Bad Request
	 * 
	 *                  { "errorMessage": "An entry with the same phone number
	 *                  already exists!", "statusCode": 400, "statusDescription":
	 *                  "Bad Request" }
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPhoneEntry(PhoneEntry pEntry) throws CustomException {
		pEntry = pEntryService.createPhoneEntry(pEntry);
		return Response.status(Response.Status.OK).entity(pEntry).build();
	}

	/**
	 * @api {put} /phoneEntries?:phoneNumber/move-to-phone-book/:pbookName Move phoneEntry to a different phoneBook
	 * @apiName movePhoneEntryToPhoneBook
	 * @apiGroup PhoneEntry
	 *
	 * @apiParam {String} phoneNumber
	 * 
	 * @apiSuccess {boolean} isMoved.
	 *
	 * @apiSuccessExample Success-Response: HTTP/1.1 200 OK 
	 * true
	 *
	 * @apiError PhoneOrNameInvalidError Phone number or phoneBook name are invalid
	 *           .
	 * 
	 * @apiErrorExample Error-Response: HTTP/1.1 400 Bad Request
	 * 
	 *                  { "errorMessage": "Phone number or phone book name is
	 *                  invalid", "statusCode": 400, "statusDescription": "Bad
	 *                  Request" }
	 */
	@PUT
	@Path("{phoneNumber}/move-to-phone-book/{pbookName}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response movePhoneEntryToPhoneBook(@PathParam("phoneNumber") String phoneNumber,
			@PathParam("pbookName") String pbookName) throws CustomException {
		boolean moved = pEntryService.movePhoneEntry(phoneNumber, pbookName);
		return Response.status(Response.Status.OK).entity(moved).build();
	}

	/**
	 * @api {put} /phoneEntries/update-phone-number?:oldPhone=val&:newPhone=val update phone number
	 * @apiName updatePhoneNumber
	 * @apiGroup PhoneEntry
	 *
	 * @apiParam {String} oldPhone
	 * @apiParam {String} newPhone
	 * 
	 * @apiSuccess {Object} phoneEntry updated PhoneEntry object.
	 *
	 * @apiSuccessExample Success-Response: HTTP/1.1 200 
	 * 						{ "firstname": "Mesa",
	 *                    "lastname": "Mlastnamesa", "phone": "000-000-000" }
	 *
	 * @apiError PhoneNumberAlreadyExistError Phone number already exists .
	 * 
	 * @apiErrorExample Error-Response: HTTP/1.1 400 Bad Request
	 * 
	 *                  { "errorMessage": "Phone entry with phone number =
	 *                  000-000-000 already exists in phone book : pb1.json",
	 *                  "statusCode": 400, "statusDescription": "Bad Request" }
	 * 
	 * 
	 * @apiError PhoneEntryNotFound Phone entry not found
	 * 
	 * @apiErrorExample Error-Response: HTTP/1.1 404 Bad Request
	 * 
	 * 
	 *                  { "errorMessage": "No phone entry found with the phone
	 *                  number : 111-222-191", "statusCode": 404,
	 *                  "statusDescription": "Not Found" }
	 */
	@PUT
	@Path("/update-phone-number")
	public Response updatePhoneNumber(@QueryParam("oldPhone") String oldPhone, @QueryParam("newPhone") String newPhone)
			throws CustomException {
		PhoneEntry pEntry = pEntryService.updatePhoneNumber(oldPhone, newPhone);
		return Response.status(Response.Status.OK).entity(pEntry).build();
	}

	/**
	 * @api {delete} /phoneEntries/:phoneNumber Delete PhoneEntry by its phone number
	 * @apiName deletePhoneEntry
	 * @apiGroup PhoneEntry
	 *
	 * @apiParam {String} phoneNumber PhoneEntry phone number.
	 *
	 * @apiSuccess {boolean} deleted
	 *
	 * @apiSuccessExample Success-Response: HTTP/1.1 200 OK
	 * 
	 *                    true
	 *
	 * @apiError PhoneEntryNotFound Phone entry not found
	 * 
	 * @apiErrorExample Error-Response: HTTP/1.1 404 Bad Request
	 * 
	 * 
	 *                  { "errorMessage": "No phone entry found with the phone
	 *                  number : 111-222-191", "statusCode": 404,
	 *                  "statusDescription": "Not Found" }
	 */
	@DELETE
	@Path("/{phoneNumber}")
	public Response deletePhoneEntry(@PathParam("phoneNumber") String phoneNumber) throws CustomException {
		boolean deleted = pEntryService.deletePhoneEntry(phoneNumber);
		return Response.status(Response.Status.OK).entity(deleted).build();
	}

	/**
	 * @api {get} /phonebooks/search/pbook/{pbookName}?:lastname=val&areaCode=val search for phone entries with criterias
	 * @apiName searchByCriteria
	 * @apiGroup PhoneEntry
	 *
	 * @apiParam {String} pbookName PhoneBook name.
	 * @apiParam {String} lastname Last name.
	 * @apiParam {String} areaCode Area code of the phone number.
	 *
	 * @apiSuccess {Object} PhoneEntries Objects matching criterias
	 * 
	 * @apiSuccessExample Success-Response: HTTP/1.1 200 OK 
	 * 						[ { "firstname": "Mesa",
	 *                    "lastname": "Mlastnamesa", "phone": "111-222-111" } ]
	 *
	 */
	@GET
	@Path("/search/pbook/{pbookName}")
	public Response searchByCriteria(@PathParam("pbookName") String pbookName, @QueryParam("lastname") String lastname,
			@QueryParam("areaCode") int areaCode) throws CustomException {
		List<PhoneEntry> result = pEntryService.findByCriteria(pbookName, lastname, areaCode);
		return Response.status(Response.Status.OK).entity(result).build();
	}

}
