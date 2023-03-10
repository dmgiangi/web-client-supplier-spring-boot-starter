package dev.dmgiangi.webclientsupplier.supplier.ssl;

import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.SecurityStoreType;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class KeyStoreFactory {
	//TODO write message
	public static final String INVALID_CONFIGURATION = "";
	private static final String KEY_STORE_ERROR = "";
	private static final String IO_ERROR = "";
	private static final String CERTIFICATE_ERROR = "";
	private static final String NO_SUCH_ALGORITH_ERROR = "";

	private KeyStoreFactory() {
	}

	public static KeyStore getKeyStore(SecurityStoreType keyStoreType, URL url, String path, String password) {
		KeyStore keyStore;
		if (StringUtils.hasText(path))
			keyStore = getKeyStoreFromPath(keyStoreType, path, password);
		else if (url != null)
			keyStore = getKeyStoreFromUrl(keyStoreType, url, password);
		else
			throw new BeanCreationException(INVALID_CONFIGURATION);

		return keyStore;
	}

	private static KeyStore getKeyStoreFromUrl(SecurityStoreType keyStoreType, URL url, String password) {
		KeyStore keyStore;
		try {
			keyStore = KeyStore.getInstance(keyStoreType.value);
		} catch (KeyStoreException e) {
			throw new BeanCreationException(KEY_STORE_ERROR);
		}

		try (InputStream stream = url.openStream()) {
			keyStore.load(stream, password.toCharArray());
		} catch (IOException e) {
			throw new BeanCreationException(IO_ERROR);
		} catch (CertificateException e) {
			throw new BeanCreationException(CERTIFICATE_ERROR);
		} catch (NoSuchAlgorithmException e) {
			throw new BeanCreationException(NO_SUCH_ALGORITH_ERROR);
		}

		return keyStore;
	}

	private static KeyStore getKeyStoreFromPath(SecurityStoreType keyStoreType, String path, String password) {
		KeyStore keyStore;
		try {
			keyStore = KeyStore.getInstance(keyStoreType.value);
		} catch (KeyStoreException e) {
			throw new BeanCreationException(INVALID_CONFIGURATION);
		}

		try (InputStream stream = Files.newInputStream(Paths.get(path))) {
			keyStore.load(stream, password.toCharArray());
		} catch (IOException e) {
			throw new BeanCreationException(IO_ERROR);
		} catch (CertificateException e) {
			throw new BeanCreationException(CERTIFICATE_ERROR);
		} catch (NoSuchAlgorithmException e) {
			throw new BeanCreationException(NO_SUCH_ALGORITH_ERROR);
		}

		return keyStore;
	}
}
