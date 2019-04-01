package edu.asupoly.ser422.lab3.exception.handler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.asupoly.ser422.lab3.exception.CustomException;
import edu.asupoly.ser422.lab3.exception.Error;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {

	@Override
	public Response toResponse(CustomException ex) {
		Response.StatusType type = Response.Status.BAD_REQUEST;

		Error error = new Error(type.getStatusCode(), type.getReasonPhrase(), ex.getLocalizedMessage());

		return Response.status(error.getStatusCode()).entity(error).type(MediaType.APPLICATION_JSON).build();
	}

}