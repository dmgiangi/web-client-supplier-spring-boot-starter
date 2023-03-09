package dev.dmgiangi.webclientsupplier.parser.dto;

import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.ClientType;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.Protocols;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientConfiguration {
    public static final Protocols DEFAULT_PROTOCOLS = Protocols.BOTH;
    public static final ClientType DEFAULT_CLIENT = ClientType.REST_TEMPLATE;
    public static final boolean DEFAULT_FAIL_FAST = true;
    private final String id;
    private final ClientType type;
    private final Protocols protocols;
    private final boolean failFast;
    private final KeyStoreConfiguration keyStore;
    private final TrustStoreConfiguration trustStore;
    private final ConnectionOptions connectionOptions;
}
