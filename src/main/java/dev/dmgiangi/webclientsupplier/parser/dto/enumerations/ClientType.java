package dev.dmgiangi.webclientsupplier.parser.dto.enumerations;

import java.util.Arrays;

public enum ClientType {
	REST_TEMPLATE, UNDEFINED;

	private static final String ACCEPTED_VALUE = "[REST_TEMPLATE]";

	public static ClientType fromString(String requestedType) {
		return Arrays.stream(ClientType.values())
					 .filter(clientType -> clientType.name().equalsIgnoreCase(requestedType))
					 .findAny()
					 .orElse(null);
	}

	public static String getAcceptedValue() {
		return ACCEPTED_VALUE;
	}
}
