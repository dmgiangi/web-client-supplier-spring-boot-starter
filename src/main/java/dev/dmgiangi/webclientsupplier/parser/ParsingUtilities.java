package dev.dmgiangi.webclientsupplier.parser;

import dev.dmgiangi.webclientsupplier.WCSConstant;
import dev.dmgiangi.webclientsupplier.parser.dto.ClientConfiguration;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.ClientType;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.Protocols;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.SecurityStoreType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class ParsingUtilities {
	public static final String CORRECTLY_DETECTED_FOR_CLIENT = "Correctly detected " + WCSConstant.WCS + ".{}{}:{}";
	public static final String FALLING_BACK_FROM_UNDEFINED = WCSConstant.WCS + ".{}{} not defined, falling back on {}";
	public static final String FALLING_BACK_FROM_UNDEFINED_WITH_ACCEPTED_IGNORING_CASE =
			FALLING_BACK_FROM_UNDEFINED + ", Accepted Value are (ignoring case): {}";
	public static final String FALLING_BACK_FROM_UNDEFINED_WITH_ACCEPTED = FALLING_BACK_FROM_UNDEFINED + ", Accepted Value are: {}";
	public static final String NON_ACCEPTABLE_VALUE = WCSConstant.WCS + ".{}{} has not acceptable value {}, falling back on {}";
	public static final String NON_ACCEPTABLE_VALUE_WITH_ACCEPTED_IGNORING_CASE =
			NON_ACCEPTABLE_VALUE + ", Accepted Value are (ignoring case): {}";
	public static final String NON_ACCEPTABLE_VALUE_WITH_ACCEPTED = NON_ACCEPTABLE_VALUE + ", Accepted Value are: {}";
	public static final String FALLING_BACK_FROM_WRONG_URL = WCSConstant.WCS + ".{}{} has a malformed value {}, falling back on {}";
	public static final String TRUE_FALSE = "[true, false]";
	public static final String INT_RANGE = "1 ~ 2147483647";
	private static final Logger LOGGER = LoggerFactory.getLogger(WCSConstant.PARSER_LOGGER_NAME);

	private ParsingUtilities() {
	}

	static Boolean parseBoolean(String value) {
		if ("true".equalsIgnoreCase(value))
			return true;
		else if ("false".equalsIgnoreCase(value))
			return false;
		else
			throw new NumberFormatException();
	}

	static boolean getBooleanProperty(Map<String, String> clientConfiguration, String id, String appender, boolean fallback) {
		final String booleanAsString = clientConfiguration.get(id + appender);

		if (booleanAsString == null) {
			if (LOGGER.isTraceEnabled())
				LOGGER.trace(FALLING_BACK_FROM_UNDEFINED_WITH_ACCEPTED_IGNORING_CASE, id, appender, fallback, TRUE_FALSE);
			return fallback;
		}

		try {
			final Boolean value = ParsingUtilities.parseBoolean(booleanAsString);
			if (LOGGER.isTraceEnabled())
				LOGGER.trace(CORRECTLY_DETECTED_FOR_CLIENT, id, appender, value);
			return value;
		} catch (NumberFormatException e) {
			if (LOGGER.isWarnEnabled())
				LOGGER.warn(NON_ACCEPTABLE_VALUE_WITH_ACCEPTED_IGNORING_CASE, id, appender, booleanAsString, fallback, TRUE_FALSE);
			return fallback;
		}
	}

	static int getIntProperty(Map<String, String> clientConfiguration, String id, String appender, int fallback) {
		final String integerAsString = clientConfiguration.get(id + appender);

		if (integerAsString == null) {
			if (LOGGER.isTraceEnabled())
				LOGGER.trace(FALLING_BACK_FROM_UNDEFINED_WITH_ACCEPTED, id, appender, fallback, INT_RANGE);

			return fallback;
		}

		int value;
		try {
			value = Integer.parseInt(integerAsString);
			if (value < 1)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			if (LOGGER.isWarnEnabled())
				LOGGER.warn(NON_ACCEPTABLE_VALUE_WITH_ACCEPTED, id, appender, integerAsString, fallback, INT_RANGE);

			return fallback;
		}

		if (LOGGER.isTraceEnabled())
			LOGGER.trace(CORRECTLY_DETECTED_FOR_CLIENT, id, appender, value);

		return value;
	}

	static String getStringProperty(Map<String, String> clientConfiguration, String id, String appender) {
		final String value = clientConfiguration.get(id + appender);

		if (!StringUtils.hasText(value)) {
			if (LOGGER.isTraceEnabled())
				LOGGER.trace(FALLING_BACK_FROM_UNDEFINED, id, appender, null);

			return null;
		}

		return value;
	}

	static URL getUrlProperty(Map<String, String> clientConfiguration, String id, String appender) {
		String value = getStringProperty(clientConfiguration, id, appender);

		if (!StringUtils.hasText(value))
			return null;

		try {
			URL url = new URL(value);
			if (LOGGER.isTraceEnabled())
				LOGGER.trace(CORRECTLY_DETECTED_FOR_CLIENT, id, appender, value);
			return url;
		} catch (MalformedURLException e) {
			if (LOGGER.isWarnEnabled())
				LOGGER.warn(FALLING_BACK_FROM_WRONG_URL, id, appender, value, null);
			return null;
		}
	}

	static ClientType getClientType(Map<String, String> clientConfiguration, String id) {
		final String clientTypeString = clientConfiguration.get(id + WCSConstant.TYPE);
		final ClientType clientType = ClientType.fromString(clientTypeString);

		if (clientType == null) {
			if (LOGGER.isWarnEnabled())
				LOGGER.warn(NON_ACCEPTABLE_VALUE_WITH_ACCEPTED_IGNORING_CASE,
							id,
							WCSConstant.TYPE,
							clientTypeString,
							ClientConfiguration.DEFAULT_CLIENT,
							ClientType.getAcceptedValue());
			return ClientConfiguration.DEFAULT_CLIENT;
		} else {
			if (LOGGER.isTraceEnabled())
				LOGGER.trace(CORRECTLY_DETECTED_FOR_CLIENT, id, WCSConstant.TYPE, clientType);
			return clientType;
		}
	}

	static Protocols getProtocols(Map<String, String> clientConfiguration, String id) {
		final String protocolsString = clientConfiguration.get(id + WCSConstant.PROTOCOLS);
		final Protocols protocols = Protocols.fromString(protocolsString);

		if (protocols == null) {
			if (LOGGER.isWarnEnabled())
				LOGGER.warn(NON_ACCEPTABLE_VALUE_WITH_ACCEPTED_IGNORING_CASE,
							id,
							WCSConstant.PROTOCOLS,
							protocolsString,
							ClientConfiguration.DEFAULT_PROTOCOLS,
							Protocols.getAcceptedValue());
			return ClientConfiguration.DEFAULT_PROTOCOLS;
		} else {
			if (LOGGER.isTraceEnabled())
				LOGGER.trace(CORRECTLY_DETECTED_FOR_CLIENT, id, WCSConstant.PROTOCOLS, protocols);
			return protocols;
		}
	}

	static SecurityStoreType getSecurityStoreType(Map<String, String> clientConfiguration, String id, String appender,
			SecurityStoreType fallback) {
		final String securityStoreTypeString = clientConfiguration.get(id + appender);
		final SecurityStoreType securityStoreType = SecurityStoreType.fromString(securityStoreTypeString);

		if (securityStoreType == null) {
			if (LOGGER.isWarnEnabled())
				LOGGER.warn(NON_ACCEPTABLE_VALUE_WITH_ACCEPTED_IGNORING_CASE,
							id,
							appender,
							securityStoreTypeString,
							fallback,
							SecurityStoreType.getAcceptedValue());
			return fallback;
		} else {
			if (LOGGER.isTraceEnabled())
				LOGGER.trace(CORRECTLY_DETECTED_FOR_CLIENT, id, appender, securityStoreType);
			return securityStoreType;
		}
	}
}
