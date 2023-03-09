package dev.dmgiangi.webclientsupplier.parser;

import dev.dmgiangi.webclientsupplier.parser.dto.ClientConfiguration;

import java.util.Map;

import static dev.dmgiangi.webclientsupplier.WCSConstant.FAIL_FAST;
import static dev.dmgiangi.webclientsupplier.parser.ConnectionOptionsFactory.createConnectionOptions;
import static dev.dmgiangi.webclientsupplier.parser.KeyStoreConfigurationFactory.createClientCertificateConfiguration;
import static dev.dmgiangi.webclientsupplier.parser.ParsingUtilities.*;
import static dev.dmgiangi.webclientsupplier.parser.TrustStoreConfigurationFactory.createTrustMaterialConfiguration;

public class ClientConfigurationFactory {
	private ClientConfigurationFactory() {
	}

	public static ClientConfiguration createClientConfiguration(Map<String, String> clientConfiguration, String id) {
		return ClientConfiguration.builder()
								  .id(id)
								  .type(getClientType(clientConfiguration, id))
								  .protocols(getProtocols(clientConfiguration, id))
								  .failFast(getFailFast(clientConfiguration, id))
								  .keyStore(createClientCertificateConfiguration(clientConfiguration, id))
								  .trustStore(createTrustMaterialConfiguration(clientConfiguration, id))
								  .connectionOptions(createConnectionOptions(clientConfiguration, id))
								  .build();
	}

	private static boolean getFailFast(Map<String, String> clientConfiguration, String id) {
		return getBooleanProperty(clientConfiguration, id, FAIL_FAST, ClientConfiguration.DEFAULT_FAIL_FAST);
	}
}
