package com.giraone.soap;

import com.giraone.soap.wsdl.DetailsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestEndpoint {

    private final Logger log = LoggerFactory.getLogger(RestEndpoint.class);

    private final BlzSoapClient blzSoapClient;

    public RestEndpoint(BlzSoapClient blzSoapClient) {
        this.blzSoapClient = blzSoapClient;
    }

    @GetMapping("/bank/{bankleitzahl}")
    public ResponseEntity<BankDetails> getBank(@PathVariable String bankleitzahl) {

        log.debug("REST request to get bank details for bankleitzahl={}", bankleitzahl);
        try {
            DetailsType details = blzSoapClient.getBank(bankleitzahl);
            BankDetails bankDetails = new BankDetails(
                details.getBezeichnung(),
                details.getBic(),
                details.getOrt(),
                details.getPlz()
            );
            log.debug("Response for bankleitzahl={} is {}", bankleitzahl, bankDetails);
            return ResponseEntity.ok(bankDetails);
        }
        catch (Exception e) {
            log.error("Retrieval of {} failed!", bankleitzahl, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
