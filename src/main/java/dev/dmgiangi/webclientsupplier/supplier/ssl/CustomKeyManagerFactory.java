package dev.dmgiangi.webclientsupplier.supplier.ssl;

import dev.dmgiangi.webclientsupplier.parser.dto.KeyStoreConfiguration;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.SecurityStoreType;
import org.springframework.beans.factory.BeanCreationException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public class CustomKeyManagerFactory {
	//TODO write message
	public static final String NO_SUCH_ALGORITHM = "";
	public static final String KEY_STORE_ERROR = "";
	public static final String UNRECOVERABLE_KEY_EXCEPTION = "";

	private CustomKeyManagerFactory() {
	}

	public static KeyManager[] getInstance(KeyStoreConfiguration keyStoreConfiguration) {
		if (SecurityStoreType.DEFAULT.equals(keyStoreConfiguration.getKeyStoreType()))
			return null;

		KeyManagerFactory keyManagerFactory;
		try {
			keyManagerFactory = KeyManagerFactory.getInstance("PKIX");
		} catch (NoSuchAlgorithmException e) {
			throw new BeanCreationException(NO_SUCH_ALGORITHM);
		}

		KeyStore keyStore = KeyStoreFactory.getKeyStore(keyStoreConfiguration.getKeyStoreType(),
														keyStoreConfiguration.getUrl(),
														keyStoreConfiguration.getPath(),
														keyStoreConfiguration.getPassword());

		try {
			keyManagerFactory.init(keyStore, keyStoreConfiguration.getPassword().toCharArray());
			return keyManagerFactory.getKeyManagers();
		} catch (UnrecoverableKeyException e) {
			throw new BeanCreationException(UNRECOVERABLE_KEY_EXCEPTION);
		} catch (KeyStoreException e) {
			throw new BeanCreationException(KEY_STORE_ERROR);
		} catch (NoSuchAlgorithmException e) {
			throw new BeanCreationException(NO_SUCH_ALGORITHM);
		}
	}
}
