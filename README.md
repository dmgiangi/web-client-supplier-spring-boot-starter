# Web Clients Supplier Spring boot Starter
Web Clients Supplier allow you to inject in the spring context beans of web client configured in multiple fashion.

The supported client at the moment are:
- _**RestTemplate**_

## Web Clients Supplier Installation
import the dependency in your pom.xml:
```xml
<dependency>
    <groupId>dev.dmgiangi</groupId>
    <artifactId>web-client-supplier-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
This library implement spring boot autoconfiguration, so to enable the behavior of Web Clients Supplier you just need to use @SpringBootApplication or @EnableAutoConfiguration on one of your beans definition.
