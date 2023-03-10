package dev.dmgiangi.webclientsupplier.parser.dto.enumerations;

import java.util.Arrays;

public enum SecurityStoreType {
	JCEKS("JCEKS"), JKS("JKS"), DKS("DKS"), PKCS11("PKCS11"), PKCS12("PKCS12"), DEFAULT("DEFAULT");

	private static final String ACCEPTED_VALUE = "[JCEKS, JKS, DKS, PKCS11, PKCS12, DEFAULT]";
	public final String value;

	public static SecurityStoreType fromString(String requestedType) {
		return Arrays.stream(SecurityStoreType.values())
					 .filter(trustStoreType -> trustStoreType.name().equalsIgnoreCase(requestedType))
					 .findAny()
					 .orElse(SecurityStoreType.DEFAULT);
	}

	SecurityStoreType(String value) {
		this.value = value;
	}

	public static String getAcceptedValue() {
		return ACCEPTED_VALUE;
	}
}