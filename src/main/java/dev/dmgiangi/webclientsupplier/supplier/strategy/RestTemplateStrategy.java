package dev.dmgiangi.webclientsupplier.supplier.strategy;

import dev.dmgiangi.webclientsupplier.parser.dto.ClientConfiguration;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.ClientType;
import dev.dmgiangi.webclientsupplier.supplier.SslContextFactory;
import dev.dmgiangi.webclientsupplier.supplier.dto.WcsBeanDefinition;
import lombok.AllArgsConstructor;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

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

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolingConnectionManager).build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

		requestFactory.setConnectTimeout(clientConfiguration.getConnectionOptions().getConnectionTimeout());
		requestFactory.setReadTimeout(clientConfiguration.getConnectionOptions().getReadTimeout());

		return new BufferingClientHttpRequestFactory(requestFactory);
	}

	private PoolingHttpClientConnectionManager getPoolingConnectionManager(ClientConfiguration clientConfiguration) {

		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();

		switch (clientConfiguration.getProtocols()) {
		case HTTP:
			registryBuilder.register(HTTP, PlainConnectionSocketFactory.getSocketFactory());
			break;
		case HTTPS:
			registryBuilder.register(HTTPS, getSSLConnectionSocketFactory(clientConfiguration));
			break;
		case BOTH:
			registryBuilder.register(HTTP, PlainConnectionSocketFactory.getSocketFactory());
			registryBuilder.register(HTTPS, getSSLConnectionSocketFactory(clientConfiguration));
			break;
		case UNDEFINED:
			throw new BeanCreationException("");
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
