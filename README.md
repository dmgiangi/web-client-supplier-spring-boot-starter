# Web Clients Supplier Spring boot Starter

Web Clients Supplier allow you to inject in the spring context beans of web client configured in multiple fashion.\
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
    <version>0.0.1</version>
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
  clientName:
    type: REST_TEMPLATE
  secondClientName:
    type: REST_TEMPLATE
```

then inject in the context with

```java
import ...

@SpringBootTest
class WebClientSupplierTestApplicationTests {
    @Autowired
    @Qualifier("clientName")
    private RestTemplate client;
    
    @Autowired
    @Qualifier("secondClientName")
    private RestTemplate secondClient;
    
    @Test
    void contextLoads() {
        assertThat(client).isNotNull();
	assertThat(secondClient).isNotNull();
    }
}
```

- full configuration:

```yaml
wcs:
  clientName:
    type: _# One of [REST_TEMPLATE, WEB_CLIENT] default -> REST_TEMPLATE
    protocols: _# One of [PLAIN, SSL_V3, TLS,TLS_V1, TLS_V1_1, TLS_V1_2, TLS_V1_3] default -> TLS
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
