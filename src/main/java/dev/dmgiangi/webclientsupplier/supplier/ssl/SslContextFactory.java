package dev.dmgiangi.webclientsupplier.supplier.ssl;

import dev.dmgiangi.webclientsupplier.parser.dto.ClientConfiguration;
import org.springframework.beans.factory.BeanCreationException;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SslContextFactory {
	//TODO write message
	public static final String KEY_MANAGEMENT_ERROR = "";
	public static final String NO_SUCH_ALGORITHM_ERROR = "";

	private SslContextFactory() {
	}

	public static SSLContext createSslContext(ClientConfiguration clientConfiguration) {
		SSLContext sslContext;
		try {
			sslContext = SSLContext.getInstance(clientConfiguration.getProtocols().value);
		} catch (NoSuchAlgorithmException e) {
			throw new BeanCreationException(NO_SUCH_ALGORITHM_ERROR);
		}

		try {
			sslContext.init(CustomKeyManagerFactory.getInstance(clientConfiguration.getKeyStore()),
							CustomTrustManagerFactory.getTrustManager(clientConfiguration.getTrustStore()),
							getSecureRandom(clientConfiguration));
		} catch (KeyManagementException e) {
			throw new BeanCreationException(KEY_MANAGEMENT_ERROR);
		}

		return sslContext;
	}

	private static SecureRandom getSecureRandom(ClientConfiguration clientConfiguration) {
		if (clientConfiguration.getSeed().length == 0)
			return null;
		else
			return new SecureRandom(clientConfiguration.getSeed());
	}
}
