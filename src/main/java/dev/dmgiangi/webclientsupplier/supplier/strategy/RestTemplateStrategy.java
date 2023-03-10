package dev.dmgiangi.webclientsupplier.supplier.strategy;

import dev.dmgiangi.webclientsupplier.parser.dto.ClientConfiguration;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.ClientType;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.Protocols;
import dev.dmgiangi.webclientsupplier.supplier.dto.WcsBeanDefinition;
import dev.dmgiangi.webclientsupplier.supplier.ssl.SslContextFactory;
import lombok.AllArgsConstructor;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;

@AllArgsConstructor
public class RestTemplateStrategy implements BeanDefinitionStrategy {
	public static final String HTTP = "http";
	public static final String HTTPS = "https";
	private final RestTemplateBuilder restTemplateBuilder;

	@Override
	public WcsBeanDefinition getBeanDefinition(ClientConfiguration clientConfiguration) {

		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(RestTemplate.class,
																							() -> restTemplateBuilder.requestFactory(() -> getRequestFactory(
																									clientConfiguration)).build())
																	 .getBeanDefinition();

		return new WcsBeanDefinition(clientConfiguration.getId(), beanDefinition);
	}

	@Override
	public boolean canHandle(ClientType clientType) {
		return ClientType.REST_TEMPLATE.equals(clientType);
	}

	private ClientHttpRequestFactory getRequestFactory(ClientConfiguration clientConfiguration) {
		PoolingHttpClientConnectionManager poolingConnectionManager = getPoolingConnectionManager(clientConfiguration);

		HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(poolingConnectionManager);

		if (clientConfiguration.getTrustStore().isIgnoreServerCertificate())
			httpClientBuilder.setSSLHostnameVerifier(getDummySslHostnameVerifier());

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());

		requestFactory.setConnectTimeout(clientConfiguration.getConnectionOptions().getConnectionTimeout());
		requestFactory.setReadTimeout(clientConfiguration.getConnectionOptions().getReadTimeout());

		return new BufferingClientHttpRequestFactory(requestFactory);
	}

	private HostnameVerifier getDummySslHostnameVerifier() {
		HostnameVerifier hostnameVerifier = (hostname, session) -> true;

		if (LOGGER.isWarnEnabled())
			LOGGER.warn("");

		return hostnameVerifier;
	}

	private PoolingHttpClientConnectionManager getPoolingConnectionManager(ClientConfiguration clientConfiguration) {

		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();

		Protocols protocols = clientConfiguration.getProtocols();

		if (Protocols.PLAIN.equals(protocols)) {
			registryBuilder.register(HTTP, PlainConnectionSocketFactory.getSocketFactory());
		} else {
			registryBuilder.register(HTTP, PlainConnectionSocketFactory.getSocketFactory());
			registryBuilder.register(HTTPS, getSSLConnectionSocketFactory(clientConfiguration));
		}

		PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(registryBuilder.build());

		poolingConnectionManager.setMaxTotal(clientConfiguration.getConnectionOptions().getMaxConnection());

		poolingConnectionManager.setDefaultMaxPerRoute(clientConfiguration.getConnectionOptions().getMaxConnectionPerRoute());

		return poolingConnectionManager;
	}

	private SSLConnectionSocketFactory getSSLConnectionSocketFactory(ClientConfiguration clientConfiguration) {
		return new SSLConnectionSocketFactory(SslContextFactory.createSslContext(clientConfiguration));
	}
}
