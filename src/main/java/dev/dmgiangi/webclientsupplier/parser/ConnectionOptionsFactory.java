package dev.dmgiangi.webclientsupplier.parser;

import dev.dmgiangi.webclientsupplier.parser.dto.ConnectionOptions;

import java.util.Map;

import static dev.dmgiangi.webclientsupplier.WCSConstant.*;
import static dev.dmgiangi.webclientsupplier.parser.ParsingUtilities.getIntProperty;
import static dev.dmgiangi.webclientsupplier.parser.dto.ConnectionOptions.*;

public class ConnectionOptionsFactory {
	private ConnectionOptionsFactory() {
	}
	
	public static ConnectionOptions createConnectionOptions(Map<String, String> clientConfiguration, String id) {
		return ConnectionOptions.builder()
								.connectionTimeout(getConnectionTimeout(clientConfiguration, id))
								.readTimeout(getReadTimeout(clientConfiguration, id))
								.maxConnection(getMaxConnection(clientConfiguration, id))
								.maxConnectionPerRoute(getMaxConnectionPerRoute(clientConfiguration, id))
								.build();
	}

	private static int getMaxConnectionPerRoute(Map<String, String> clientConfiguration, String id) {
		return getIntProperty(clientConfiguration, id, MAX_CONNECTION_PER_ROUTE, DEFAULT_MAX_CONNECTION_PER_ROUTE);
	}

	private static int getMaxConnection(Map<String, String> clientConfiguration, String id) {
		return getIntProperty(clientConfiguration, id, MAX_CONNECTION, DEFAULT_MAX_CONNECTION);
	}

	private static int getReadTimeout(Map<String, String> clientConfiguration, String id) {
		return getIntProperty(clientConfiguration, id, READ_TIMEOUT, DEFAULT_READ_TIMEOUT);
	}

	private static int getConnectionTimeout(Map<String, String> clientConfiguration, String id) {
		return getIntProperty(clientConfiguration, id, CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
	}
}
