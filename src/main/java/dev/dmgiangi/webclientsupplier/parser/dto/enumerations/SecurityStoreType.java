package dev.dmgiangi.webclientsupplier.parser.dto.enumerations;

import java.util.Arrays;

public enum SecurityStoreType {
	JKS, PKCS12, PKCS8, DEFAULT;

	private static final String ACCEPTED_VALUE = "[JKS, PKCS12, PKCS8, DEFAULT]";

	public static SecurityStoreType fromString(String requestedType) {
		return Arrays.stream(SecurityStoreType.values())
					 .filter(trustStoreType -> trustStoreType.name().equalsIgnoreCase(requestedType))
					 .findAny()
					 .orElse(SecurityStoreType.DEFAULT);
	}

	public static String getAcceptedValue() {
		return ACCEPTED_VALUE;
	}
}