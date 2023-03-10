package dev.dmgiangi.webclientsupplier.parser.dto;

import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.ClientType;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.Protocols;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientConfiguration {
    public static final Protocols DEFAULT_PROTOCOLS = Protocols.TLS;
    public static final ClientType DEFAULT_CLIENT = ClientType.REST_TEMPLATE;
    private final String id;
    private final ClientType type;
    private final Protocols protocols;
    private final KeyStoreConfiguration keyStore;
    private final TrustStoreConfiguration trustStore;
    private final ConnectionOptions connectionOptions;
    private final byte[] seed;
}
