package dev.dmgiangi.webclientsupplier.parser;

import dev.dmgiangi.webclientsupplier.WCSConstant;
import dev.dmgiangi.webclientsupplier.parser.dto.TrustStoreConfiguration;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.SecurityStoreType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Map;

import static dev.dmgiangi.webclientsupplier.WCSConstant.*;
import static dev.dmgiangi.webclientsupplier.parser.ParsingUtilities.*;
import static dev.dmgiangi.webclientsupplier.parser.dto.TrustStoreConfiguration.DEFAULT_IGNORE_SERVER_CERTIFICATE;
import static dev.dmgiangi.webclientsupplier.parser.dto.TrustStoreConfiguration.DEFAULT_TRUSTSTORE;

public class TrustStoreConfigurationFactory {
	public static final String WARN_IGNORE_SERVER_CERTIFICATE_ENABLED = "{} CLIENT HAS IGNORE SERVER CERTIFICATE ENABLED. THIS BEHAVIOUR SHOULD BE USED ONLY FOR TESTING PURPOSE";
	private static final Logger LOGGER = LoggerFactory.getLogger(WCSConstant.PARSER_LOGGER_NAME);

	private TrustStoreConfigurationFactory() {
	}

	public static TrustStoreConfiguration createTrustMaterialConfiguration(Map<String, String> clientConfiguration, String id) {
		return TrustStoreConfiguration.builder()
									  .url(getTrustStoreUrl(clientConfiguration, id))
									  .path(getTruststorePath(clientConfiguration, id))
									  .password(getTrustStorePassword(clientConfiguration, id))
									  .trustStoreType(getTrustStoreType(clientConfiguration, id))
									  .ignoreServerCertificate(getIgnoreServerCertificate(clientConfiguration, id))
									  .build();
	}

	private static SecurityStoreType getTrustStoreType(Map<String, String> clientConfiguration, String id) {
		return getTrustStore(clientConfiguration, id, TRUSTSTORE_TYPE, DEFAULT_TRUSTSTORE);
	}

	private static String getTruststorePath(Map<String, String> clientConfiguration, String id) {
		return getStringProperty(clientConfiguration, id, TRUSTSTORE_PATH);
	}

	private static String getTrustStorePassword(Map<String, String> clientConfiguration, String id) {
		return getStringProperty(clientConfiguration, id, TRUSTSTORE_PASSWORD);
	}

	private static URL getTrustStoreUrl(Map<String, String> clientConfiguration, String id) {
		return getUrlProperty(clientConfiguration, id, TRUSTSTORE_URL);
	}

	private static boolean getIgnoreServerCertificate(Map<String, String> clientConfiguration, String id) {
		boolean ignoreServerCertificate = getBooleanProperty(clientConfiguration,
															 id,
															 TRUSTSTORE_IGNORE_SERVER_CERTIFICATE,
															 DEFAULT_IGNORE_SERVER_CERTIFICATE);

		if (ignoreServerCertificate && LOGGER.isWarnEnabled())
			LOGGER.warn(WARN_IGNORE_SERVER_CERTIFICATE_ENABLED, id);

		return ignoreServerCertificate;
	}
}
