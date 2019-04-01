package edu.asupoly.ser422.lab3.config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import edu.asupoly.ser422.lab3.model.PhoneEntry;

@Provider
@Consumes("application/json")
public class PhoneEntryMessageBodyReader implements MessageBodyReader<PhoneEntry> {

	@Override
	public boolean isReadable(Class<?> arg0, Type type, Annotation[] arg2, MediaType arg3) {
		return (type == PhoneEntry.class);
	}

	@Override
	public PhoneEntry readFrom(Class<PhoneEntry> arg0, Type arg1, Annotation[] arg2, MediaType arg3,
			MultivaluedMap<String, String> arg4, InputStream entityStream) throws IOException, WebApplicationException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(PhoneEntry.class);
			return (PhoneEntry) jaxbContext.createUnmarshaller().unmarshal(entityStream);
		} catch (JAXBException e) {
			throw new ProcessingException("Error deserializing phone entry.", e);
		}

	}
}
