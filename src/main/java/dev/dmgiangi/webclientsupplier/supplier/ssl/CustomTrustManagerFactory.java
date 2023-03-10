package dev.dmgiangi.webclientsupplier.supplier.ssl;

import dev.dmgiangi.webclientsupplier.parser.dto.TrustStoreConfiguration;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.SecurityStoreType;
import org.springframework.beans.factory.BeanCreationException;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class CustomTrustManagerFactory {
	//TODO write message
	public static final String NO_SUCH_ALGORITHM = "";
	public static final String KEY_STORE_ERROR = "";

	private CustomTrustManagerFactory() {
	}

	public static TrustManager[] getTrustManager(TrustStoreConfiguration trustStoreConfiguration) {
		if (SecurityStoreType.DEFAULT.equals(trustStoreConfiguration.getTrustStoreType()))
			return null;

		TrustManagerFactory trustManagerFactory;
		try {
			trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
		} catch (NoSuchAlgorithmException e) {
			throw new BeanCreationException(NO_SUCH_ALGORITHM);
		}

		KeyStore keyStore = KeyStoreFactory.getKeyStore(trustStoreConfiguration.getTrustStoreType(),
														trustStoreConfiguration.getUrl(),
														trustStoreConfiguration.getPath(),
														trustStoreConfiguration.getPassword());

		try {
			trustManagerFactory.init(keyStore);
			return trustManagerFactory.getTrustManagers();
		} catch (KeyStoreException e) {
			throw new BeanCreationException(KEY_STORE_ERROR);
		}
	}
}
