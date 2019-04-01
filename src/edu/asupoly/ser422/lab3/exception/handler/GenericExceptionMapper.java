package edu.asupoly.ser422.lab3.exception.handler;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.asupoly.ser422.lab3.exception.Error;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception ex) {
		Response.StatusType type = getStatusType(ex);

		Error error = new Error(type.getStatusCode(), type.getReasonPhrase(), ex.getLocalizedMessage());

		return Response.status(error.getStatusCode()).entity(error).type(MediaType.APPLICATION_JSON).build();
	}

	private Response.StatusType getStatusType(Throwable ex) {
		if (ex instanceof WebApplicationException) {
			return ((WebApplicationException) ex).getResponse().getStatusInfo();
		} else {
			return Response.Status.INTERNAL_SERVER_ERROR;
		}
	}
}