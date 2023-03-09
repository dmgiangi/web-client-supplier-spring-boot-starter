package dev.dmgiangi.webclientsupplier.supplier;

import dev.dmgiangi.webclientsupplier.parser.dto.ClientConfiguration;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.SecurityStoreType;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class SslContextFactory {
	private SslContextFactory() {
	}

	public static SSLContext createSslContext(ClientConfiguration clientConfiguration) {

		//TODO refactor this method
		SSLContextBuilder sslContextBuilder = new SSLContextBuilder();

		final String trustStorePath = clientConfiguration.getTrustStore().getPath();
		final URL trustStoreUrl = clientConfiguration.getTrustStore().getUrl();
		final String trustStorePassword = clientConfiguration.getTrustStore().getPassword();
		final SecurityStoreType trustStoreType = clientConfiguration.getTrustStore().getTrustStoreType();
		final boolean ignoreServerCertificate = clientConfiguration.getTrustStore().isIgnoreServerCertificate();

		switch (trustStoreType) {
		case JKS:
		case PKCS12:
		case PKCS8:
			try {
				if (StringUtils.hasText(trustStorePath)) {
					if (StringUtils.hasText(trustStorePassword)) {
						if (ignoreServerCertificate) {
							sslContextBuilder.loadTrustMaterial(new File(trustStorePath),
																trustStorePassword.toCharArray(),
																(x509Certificates, s) -> true);
						} else {
							sslContextBuilder.loadTrustMaterial(new File(trustStorePath), trustStorePassword.toCharArray());
						}
					} else {
						throw new BeanCreationException("");
					}
				} else if (trustStoreUrl != null) {
					if (StringUtils.hasText(trustStorePassword)) {
						if (ignoreServerCertificate) {
							sslContextBuilder.loadTrustMaterial(trustStoreUrl,
																trustStorePassword.toCharArray(),
																(x509Certificates, s) -> true);
						} else
							sslContextBuilder.loadTrustMaterial(trustStoreUrl, trustStorePassword.toCharArray());
					} else
						throw new BeanCreationException("");
				} else
					throw new BeanCreationException("");
			} catch (CertificateException e) {
				throw new BeanCreationException("a");
			} catch (NoSuchAlgorithmException e) {
				throw new BeanCreationException("b");
			} catch (KeyStoreException e) {
				throw new BeanCreationException("c");
			} catch (IOException e) {
				throw new BeanCreationException("d");
			}
			break;
		case DEFAULT:
		}

		final String keyStorePath = clientConfiguration.getKeyStore().getPath();
		final URL keyStoreUrl = clientConfiguration.getKeyStore().getUrl();
		final String keyStorePassword = clientConfiguration.getKeyStore().getPassword();
		final SecurityStoreType keyStoreType = clientConfiguration.getKeyStore().getKeyStoreType();

		switch (keyStoreType) {
		case JKS:
		case PKCS12:
		case PKCS8:
			try {
				sslContextBuilder.setKeyStoreType(keyStoreType.name());
				if (StringUtils.hasText(keyStorePath)) {
					if (StringUtils.hasText(keyStorePassword)) {
						sslContextBuilder.loadTrustMaterial(new File(keyStorePath), keyStorePassword.toCharArray());
					} else {
						sslContextBuilder.loadTrustMaterial(new File(keyStorePath));
					}
				} else if (keyStoreUrl != null) {
					if (StringUtils.hasText(keyStorePassword)) {
						sslContextBuilder.loadTrustMaterial(keyStoreUrl, keyStorePassword.toCharArray());
					} else {
						throw new BeanCreationException("");
					}
				} else {
					throw new BeanCreationException("");
				}
			} catch (CertificateException e) {
				throw new BeanCreationException("a");
			} catch (NoSuchAlgorithmException e) {
				throw new BeanCreationException("b");
			} catch (KeyStoreException e) {
				throw new BeanCreationException("c");
			} catch (IOException e) {
				throw new BeanCreationException("d");
			}
			break;
		case DEFAULT:
		}

		try {
			return sslContextBuilder.build();
		} catch (NoSuchAlgorithmException e) {
			throw new BeanCreationException("a");
		} catch (KeyManagementException e) {
			throw new BeanCreationException("b");
		}
	}
}
