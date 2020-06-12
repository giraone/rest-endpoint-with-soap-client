package com.giraone.soap;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Configuration
public class BlzSoapConfiguration {

    public static final String SOAP_ENDPOINT = "http://www.thomas-bayer.com/axis2/services/BLZService";
    public static final String DEFAULT_URI = "http://thomas-bayer.com/blz/";

    private static final int SOAP_WEBCLIENT_CONNECT_TIMEOUT_SECONDS = 30;
    private static final int SOAP_WEBCLIENT_IO_TIMEOUT_SECONDS = 30;
    private static final int MAX_DATA_BUFFER_SIZE = 100 * 1024;


    private static final Logger log = LoggerFactory.getLogger(BlzSoapConfiguration.class);

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in pom.xml

        // Solution (1) - use context path and a manipulated WSDL/XSD
        marshaller.setContextPath("com.giraone.soap.wsdl");

        // Solution (2) - manually define the classes and suppress XmlRootElement checks
        // marshaller.setClassesToBeBound(GetBankType.class, GetBankResponseType.class, DetailsType.class);
        // marshaller.setSupportJaxbElementClass(true);
        // marshaller.setCheckForXmlRootElement(false);

        return marshaller;
    }

    // reactive configuration - see https://stackoverflow.com/a/60709721

    @Bean(name = "soapWebClient")
    public WebClient soapWebClient(WebClient.Builder webClientBuilder) {

        log.info("Initializing SOAP Web Client ({}) bean...", SOAP_ENDPOINT);

        return webClientBuilder.baseUrl(SOAP_ENDPOINT)
            .defaultHeader(CONTENT_TYPE, "application/soap+xml")
            // if you have any time limitation put them here
            .clientConnector(getWebClientConnector(SOAP_WEBCLIENT_CONNECT_TIMEOUT_SECONDS, SOAP_WEBCLIENT_IO_TIMEOUT_SECONDS))
            // if you have any request/response size limitation put them here as well
            .exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                    .maxInMemorySize(MAX_DATA_BUFFER_SIZE))
                .build())
            .build();
    }

    public static ReactorClientHttpConnector getWebClientConnector(int connectTimeoutSeconds, int ioTimeoutSeconds) {
        TcpClient tcpClient = TcpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutSeconds * 1000)
            .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(ioTimeoutSeconds))
                .addHandlerLast(new WriteTimeoutHandler(ioTimeoutSeconds)));
        return new ReactorClientHttpConnector(HttpClient.from(tcpClient));
    }
}
