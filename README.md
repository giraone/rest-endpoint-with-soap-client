# Example Spring Boot App with SOAP client

Three sample Spring Boot Apps:

1. A Spring Boot app with a REST endpoint for fetch bank infos dependening on a "Bankleitzahl"
1. A Spring Boot MVC app with a SOAP client to 1. offering itself a REST endpoint 
1. A Spring Boot WebFlux app with a SOAP client to 1. offering itself a REST endpoint 

### Notes

The project is set up to use JDK 11.

The [original WSDL](soap-client-mvc/src/main/resources/blz-service-orig.wsdl) loadable from
http://www.thomas-bayer.com/axis2/services/BLZService?wsdl was adapted due to problems in the code generation
(XmlRootElements were not generated when `xsd:complexType`s were referenced by name).
Either the [adapted WSDL](soap-client-mvc/src/main/resources/blz-service.wsdl) or the even
[simpler XSD](soap-client-mvc/src/main/resources/blz-service.xsd) can be used.

### Build & Run

- Run `mvn package` in each directory
- Run SOAP service app in [soap-service-blz](soap-service-blz): `java -jar target/soap-service-blz.jar` 
- Run MVC app in [soap-client-mvc](soap-client-mvc): `java -jar target/soap-client-mvc.jar` 
- Use MVC app: `http://localhost:8081/`
- Run WebFlux app [soap-client-reactive](soap-client-reactive): `java -jar target/soap-client-reactive.jar` 
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
