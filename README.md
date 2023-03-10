# Web Clients Supplier Spring boot Starter

Web Clients Supplier allow you to inject in the spring context beans of web
client configured in multiple fashion.\
The supported client at the moment are:

- _**RestTemplate**_
- _**WebClient**_ _(coming soon)_

## Spring support

This library currently chooses to support spring boot 2.x.x due to its wider
use.\
When the first stable release is released, support for spring boot 3.x.x will
most likely be introduced.

## Web Clients Supplier Installation

import the dependency in your pom.xml:

```xml

<dependency>
    <groupId>dev.dmgiangi</groupId>
    <artifactId>web-client-supplier-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

This library implement spring boot autoconfiguration, so to enable the behavior
of Web Clients Supplier you just need to use @SpringBootApplication or
@EnableAutoConfiguration on one of your beans definition.

## Create Web Client Bean

In order to inject in the spring context a web client bean you just need to
insert in your configuration the following configuration.\

- minimal configuration:

```yaml
wcs:
  WebClientBeanName1:
    type: REST_TEMPLATE
  WebClientBeanName2:
    type: WEB_CLIENT
```

then inject in the context with

```java
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

public class YourClass1 {
	private final RestTemplate restTemplate;

	public YourClass1(
			@Qualifier("WebClientBeanName1") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
}

public class YourClass2 {
	private final WebClient webClient;

	public YourClass2(@Qualifier("WebClientBeanName2") WebClient webClient) {
		this.webClient = webClient;
	}
}
```

- full configuration:

```yaml
wcs:
  clientName:
    type: _# One of [REST_TEMPLATE, WEB_CLIENT] default -> REST_TEMPLATE
    protocols: _# One of [PLAIN, SSL_V3, TLS,TLS_V1, TLS_V1_1, TLS_V1_2, TLS_V1_3] default -> BOTH
    secureSeed: _# A random string for generation of secure random in encrypted connection. default -> generated at runtime
    keystore:
      path: _# todo
      password: _# todo
      url: _# todo
      type: _# One of [JCEKS, JKS, DKS, PKCS11, PKCS12, DEFAULT]
    timeout:
      maxConnection: _# Positive integer number
      maxConnectionPerRoute: _# Positive integer number
      read: _# Positive integer number
      connection: _# Positive integer number
    trustStore:
      path: _# todo
      password: _# todo
      url: _# todo
      type: _# One Of [JCEKS, JKS, DKS, PKCS11, PKCS12, DEFAULT]
      ignoreServerCertificate: _# One of [true, false] default -> false
```