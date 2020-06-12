package com.giraone.soap;

import com.giraone.soap.wsdl.DetailsType;
import com.giraone.soap.wsdl.GetBankRequest;
import com.giraone.soap.wsdl.GetBankResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class BlzSoapClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(BlzSoapClient.class);

    @Autowired
    ApplicationProperties applicationProperties;

    public DetailsType getBank(String bankleitzahl) {

        final GetBankRequest requestPayload = new GetBankRequest();
        requestPayload.setBlz(bankleitzahl);

        final String endpoint = applicationProperties.getSoapEndpointUrl();
        final String defaultUri = applicationProperties.getDefaultUri();
        log.info("Requesting bank for bankleitzahl \"{}\" from {}", bankleitzahl, endpoint);

        final SoapActionCallback requestCallback = new SoapActionCallback(defaultUri);
        GetBankResponse responsePayload = (GetBankResponse) getWebServiceTemplate()
            .marshalSendAndReceive(endpoint, requestPayload, requestCallback);

        return responsePayload.getDetails();
    }
}