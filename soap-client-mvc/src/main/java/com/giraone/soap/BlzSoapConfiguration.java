package com.giraone.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class BlzSoapConfiguration {

    @Autowired
    ApplicationProperties applicationProperties;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in pom.xml

        // Solution (1) - use context path and a manipulated WSDL/XSD
        marshaller.setContextPath(applicationProperties.getContextPath());

        // Solution (2) - manually define the classes and suppress XmlRootElement checks
        // marshaller.setClassesToBeBound(GetBank.class, GetBankResponseType.class, DetailsType.class);
        // marshaller.setSupportJaxbElementClass(true);
        // marshaller.setCheckForXmlRootElement(false);

        return marshaller;
    }

    @Bean
    public BlzSoapClient blzSoapClient(Jaxb2Marshaller marshaller) {
        BlzSoapClient client = new BlzSoapClient();
        client.setDefaultUri(applicationProperties.getSoapEndpointUrl());
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
