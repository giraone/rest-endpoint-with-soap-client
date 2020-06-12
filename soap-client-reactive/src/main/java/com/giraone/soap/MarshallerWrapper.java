package com.giraone.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringWriter;

@Component
public class MarshallerWrapper {

    @Autowired
    private Jaxb2Marshaller marshaller;

    // marshals one object (of your bound classes) into a String.
    public <T> String marshallXml(final T obj) {
        final StringWriter writer = new StringWriter();
        final Result result = new StreamResult(writer);
        marshaller.marshal(obj, result);
        return writer.toString();
    }

    // (tries to) unmarshall(s) an InputStream to the desired object.
    @SuppressWarnings("unchecked")
    public <T> T unmarshallXml(final String xml) {
        return (T) marshaller.unmarshal(new StreamSource(xml));
    }
}
