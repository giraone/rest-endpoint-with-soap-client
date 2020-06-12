package com.giraone.soap;

import com.giraone.soap.wsdl.DetailsType;
import com.giraone.soap.wsdl.GetBank;
import io.netty.handler.timeout.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.net.ConnectException;

import static org.springframework.http.HttpStatus.GATEWAY_TIMEOUT;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Component
public class BlzSoapClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(BlzSoapClient.class);

    private final WebClient soapWebClient;
    private final MarshallerWrapper marshallerWrapper;

    public BlzSoapClient(@Qualifier("soapWebClient") WebClient soapWebClient, MarshallerWrapper marshallerWrapper) {
        this.soapWebClient = soapWebClient;
        this.marshallerWrapper = marshallerWrapper;
    }

    public Mono<Tuple2<HttpStatus, DetailsType>> getBank(String bankleitzahl) {

        final GetBank requestPayload = new GetBank();
        requestPayload.setBlz(bankleitzahl);

        log.info("Requesting bank for bankleitzahl \"{}\"", bankleitzahl);
        String xmlPayloadString = marshallerWrapper.marshallXml(requestPayload);

        return this.send(xmlPayloadString).map(tuple2 -> {
            final DetailsType detailsType = marshallerWrapper.unmarshallXml(tuple2.getT2());
            return Tuples.of(tuple2.getT1(), detailsType);
        });
    }

    public Mono<Tuple2<HttpStatus, String>> send(String soapXML) {

        return Mono.just("Request:\n" + soapXML)
            .doOnNext(log::info)
            .flatMap(xml -> soapWebClient.post()
                .bodyValue(soapXML)
                .exchange()
                .doOnNext(res -> log.info("response status code: [{}]", res.statusCode()))
                .flatMap(res -> res.bodyToMono(String.class)
                    .doOnNext(body -> log.info("Response body:\n{}", body))
                    .map(b -> Tuples.of(res.statusCode(), b))
                    .defaultIfEmpty(Tuples.of(res.statusCode(), "There is no data in the response"))))

            .onErrorResume(ConnectException.class, e -> Mono.just(Tuples.of(SERVICE_UNAVAILABLE, "Failed to connect to server"))
                .doOnEach(tuple2 -> log.warn(tuple2.toString())))
            .onErrorResume(TimeoutException.class, e -> Mono.just(Tuples.of(GATEWAY_TIMEOUT, "There is no response from the server"))
                .doOnEach(tuple2 -> log.warn(tuple2.toString())));
    }

}