package dev.dmgiangi.webclientsupplier.parser.dto;

import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.SecurityStoreType;
import lombok.Builder;
import lombok.Getter;

import java.net.URL;

@Getter
@Builder
public class TrustStoreConfiguration {
	public static final boolean DEFAULT_IGNORE_SERVER_CERTIFICATE = false;
	public static final SecurityStoreType DEFAULT_TRUSTSTORE = SecurityStoreType.DEFAULT;
	private final URL url;
	private final String path;
	private final String password;
	private final SecurityStoreType trustStoreType;
	private final boolean ignoreServerCertificate;
}
