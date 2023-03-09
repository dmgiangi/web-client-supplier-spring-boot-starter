package dev.dmgiangi.webclientsupplier.parser.dto.enumerations;

import java.util.Arrays;

public enum Protocols {
	HTTP, HTTPS, BOTH, UNDEFINED;

	private static final String ACCEPTED_VALUE = "[HTTP, HTTPS, BOTH]";

	public static Protocols fromString(String requestedType) {
		return Arrays.stream(Protocols.values())
					 .filter(protocol -> protocol.name().equalsIgnoreCase(requestedType))
					 .findAny()
					 .orElse(Protocols.UNDEFINED);
	}

	public static String getAcceptedValue() {
		return ACCEPTED_VALUE;
	}
}
