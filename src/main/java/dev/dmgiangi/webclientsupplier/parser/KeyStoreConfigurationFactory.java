package dev.dmgiangi.webclientsupplier.parser;

import dev.dmgiangi.webclientsupplier.parser.dto.KeyStoreConfiguration;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.SecurityStoreType;

import java.net.URL;
import java.util.Map;

import static dev.dmgiangi.webclientsupplier.WCSConstant.*;
import static dev.dmgiangi.webclientsupplier.parser.ParsingUtilities.*;

public class KeyStoreConfigurationFactory {
	private KeyStoreConfigurationFactory() {
	}

	public static KeyStoreConfiguration createClientCertificateConfiguration(Map<String, String> clientConfiguration, String id) {
		return KeyStoreConfiguration.builder()
									.url(getClientCertificateUrl(clientConfiguration, id))
									.path(getClientCertificatePath(clientConfiguration, id))
									.password(getClientCertificatePassword(clientConfiguration, id))
									.keyStoreType(getClientSideTrustStore(clientConfiguration, id))
									.build();
	}

	private static SecurityStoreType getClientSideTrustStore(Map<String, String> clientConfiguration, String id) {
		return getSecurityStoreType(clientConfiguration, id, KEYSTORE_TYPE, KeyStoreConfiguration.DEFAULT_TRUSTSTORE);
	}

	private static String getClientCertificatePassword(Map<String, String> clientConfiguration, String id) {
		return getStringProperty(clientConfiguration, id, KEYSTORE_PASSWORD);
	}

	private static String getClientCertificatePath(Map<String, String> clientConfiguration, String id) {
		return getStringProperty(clientConfiguration, id, KEYSTORE_PATH);
	}

	private static URL getClientCertificateUrl(Map<String, String> clientConfiguration, String id) {
		return getUrlProperty(clientConfiguration, id, KEYSTORE_URL);
	}
}
