package dev.dmgiangi.webclientsupplier.parser.dto.enumerations;

import java.util.Arrays;

public enum Protocols {
	PLAIN("http"), SSL_V3("SSLv3"), TLS("TLS"), TLS_V1("TLSv1"), TLS_V1_1("TLSv1.1"), TLS_V1_2("TLSv1.2"), TLS_V1_3("TLSv1.3");

	private static final String ACCEPTED_VALUE = "[PLAIN, SSL_V3, TLS,TLS_V1, TLS_V1_1, TLS_V1_2, TLS_V1_3]";
	public final String value;

	Protocols(String value) {
		this.value = value;
	}

	public static Protocols fromString(String requestedType) {
		return Arrays.stream(Protocols.values()).filter(protocol -> protocol.name().equalsIgnoreCase(requestedType)).findAny().orElse(null);
	}

	public static String getAcceptedValue() {
		return ACCEPTED_VALUE;
	}
}
