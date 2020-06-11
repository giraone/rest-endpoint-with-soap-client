package com.giraone.soap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestEndpointIntTest {

    private static final String BASE_URL = "/api";

    @Autowired
    private WebTestClient webClient;

    @Test
    public void assertThat_getBank_worksBasically() {

        String bankleitzahl = "10070024";

        BankDetails result = webClient.get()
            .uri(BASE_URL + "/bank/{0}", bankleitzahl)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .returnResult(BankDetails.class)
            .getResponseBody()
            .blockFirst()
        ;

        assertThat(result).isNotNull();
        assertThat(result.getBic()).isEqualTo("DEUTDEDBBER");
    }
}