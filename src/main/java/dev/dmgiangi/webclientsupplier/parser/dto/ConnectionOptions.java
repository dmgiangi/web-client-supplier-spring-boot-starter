package dev.dmgiangi.webclientsupplier.parser.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConnectionOptions {
	public static final int DEFAULT_MAX_CONNECTION = 10;
	public static final int DEFAULT_MAX_CONNECTION_PER_ROUTE = 10;
	public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;
	public static final int DEFAULT_READ_TIMEOUT = 60000;
	private final int maxConnection;
	private final int maxConnectionPerRoute;
	private final int readTimeout;
	private final int connectionTimeout;
}
