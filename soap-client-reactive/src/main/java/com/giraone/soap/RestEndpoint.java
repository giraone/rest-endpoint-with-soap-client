package com.giraone.soap;

import com.giraone.soap.wsdl.DetailsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class RestEndpoint {

    private final Logger log = LoggerFactory.getLogger(RestEndpoint.class);

    private final BlzSoapClient blzSoapClient;

    public RestEndpoint(BlzSoapClient blzSoapClient) {
        this.blzSoapClient = blzSoapClient;
    }

    @GetMapping("/bank/{bankleitzahl}")
    public Mono<BankDetails> getBank(@PathVariable String bankleitzahl) {

        log.debug("REST request to get bank details for bankleitzahl={}", bankleitzahl);
        return blzSoapClient.getBank(bankleitzahl)
            .map(tuple2 -> {
                final DetailsType details = tuple2.getT2();
                final BankDetails bankDetails = new BankDetails(
                    details.getBezeichnung(),
                    details.getBic(),
                    details.getOrt(),
                    details.getPlz()
                );
                log.debug("Response for bankleitzahl={} is {}", bankleitzahl, bankDetails);
                return bankDetails;
            });
    }
}
