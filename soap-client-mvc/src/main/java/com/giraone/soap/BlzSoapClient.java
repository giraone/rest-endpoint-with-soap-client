package com.giraone.soap;

import com.giraone.soap.wsdl.DetailsType;
import com.giraone.soap.wsdl.GetBank;
import com.giraone.soap.wsdl.GetBankResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import static com.giraone.soap.BlzSoapConfiguration.DEFAULT_URI;
import static com.giraone.soap.BlzSoapConfiguration.SOAP_ENDPOINT;

public class BlzSoapClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(BlzSoapClient.class);

    public DetailsType getBank(String bankleitzahl) {

        final GetBank requestPayload = new GetBank();
        requestPayload.setBlz(bankleitzahl);

        log.info("Requesting bank for bankleitzahl \"{}\"", bankleitzahl);

        final SoapActionCallback requestCallback = new SoapActionCallback(DEFAULT_URI);
        // uri, requestPayload, requestCallback
        GetBankResponse responsePayload = (GetBankResponse) getWebServiceTemplate()
            .marshalSendAndReceive(SOAP_ENDPOINT, requestPayload, requestCallback);

        return responsePayload.getDetails();
    }

}