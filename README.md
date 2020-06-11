# Example Spring Boot App with SOAP client

Two sample Spring Boot Apps that

- offer a REST endpoint as a wrapper
-  for an underlying SOAP service

in both

- imperative style (MVC)
- reactive style (WebFlux)

### Notes

The project is set up to use JDK 11.

The [original WSDL](soap-client-mvc/src/main/resources/blz-service-orig.wsdl) was adapted due to problems in the code generation
(XmlRootElements were not generated when `xsd:complexType`s were referenced by name).
The [adapted WSDL](soap-client-mvc/src/main/resources/blz-service.wsdl)

### Build & Run

- Run `mvn package` in each directory
- Run MVC app: `java -jar target/soap-client-mvc.jar` 
- Use MVC app: `http://localhost:8080/`
- Run WebFlux app: `java -jar target/soap-client-reactive.jar` 
- Use WebFlux app: `http://localhost:8082/`

### Links

- The used [Bankleitzahlen Web Service](https://www.predic8.de/soap/blz-webservice.htm) and its [BLZService?wsdl WSDL](http://www.thomas-bayer.com/axis2/services/BLZService?wsdl)
- Spring Documentation for using SOAP Clients
  - [Spring IO Guide for SOAP Clients](https://spring.io/guides/gs/consuming-web-service/)
  - [Spring Sample Code on GitHub](https://github.com/spring-guides/gs-consuming-web-service)
- JAXB Documentation
  - [JAXB2 Maven Plugin Documentation for HighSource JAXB2 Maven Plugin](https://github.com/highsource/maven-jaxb2-plugin/wiki)
  - [its Configuration-Cheat-Sheet](https://github.com/highsource/maven-jaxb2-plugin/wiki/Configuration-Cheat-Sheet)
  - [How to manipulate the WSDL/XSD to get XmlRootElements generated](https://stackoverflow.com/a/15117152)
