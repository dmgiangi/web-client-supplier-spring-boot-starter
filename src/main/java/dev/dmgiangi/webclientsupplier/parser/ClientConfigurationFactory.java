package dev.dmgiangi.webclientsupplier.parser;

import dev.dmgiangi.webclientsupplier.parser.dto.ClientConfiguration;

import java.util.Map;

import static dev.dmgiangi.webclientsupplier.WCSConstant.RANDOM_SEED;
import static dev.dmgiangi.webclientsupplier.parser.ConnectionOptionsFactory.createConnectionOptions;
import static dev.dmgiangi.webclientsupplier.parser.KeyStoreConfigurationFactory.createClientCertificateConfiguration;
import static dev.dmgiangi.webclientsupplier.parser.ParsingUtilities.getClientType;
import static dev.dmgiangi.webclientsupplier.parser.ParsingUtilities.getProtocols;
import static dev.dmgiangi.webclientsupplier.parser.TrustStoreConfigurationFactory.createTrustMaterialConfiguration;

public class ClientConfigurationFactory {

	private ClientConfigurationFactory() {
	}

	public static ClientConfiguration createClientConfiguration(Map<String, String> clientConfiguration, String id) {
		return ClientConfiguration.builder()
								  .id(id)
								  .type(getClientType(clientConfiguration, id))
								  .protocols(getProtocols(clientConfiguration, id))
								  .keyStore(createClientCertificateConfiguration(clientConfiguration, id))
								  .trustStore(createTrustMaterialConfiguration(clientConfiguration, id))
								  .connectionOptions(createConnectionOptions(clientConfiguration, id))
								  .seed(getSeed(clientConfiguration, id))
								  .build();
	}

	private static byte[] getSeed(Map<String, String> clientConfiguration, String id) {
		String value = ParsingUtilities.getStringProperty(clientConfiguration, id, RANDOM_SEED);

		if (value == null)
			return new byte[0];
		else
			return value.getBytes();
	}
}
