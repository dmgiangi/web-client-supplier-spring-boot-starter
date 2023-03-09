package dev.dmgiangi.webclientsupplier.parser;

import dev.dmgiangi.webclientsupplier.WCSConstant;
import dev.dmgiangi.webclientsupplier.parser.dto.ClientConfiguration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ConfigurationParser {
	private final AbstractEnvironment environment;

	public ConfigurationParser(AbstractEnvironment environment) {
		this.environment = environment;
	}

	private static List<ClientConfiguration> computeClientConfigurations(Map<String, Map<String, String>> rawClientConfigurations) {
		return rawClientConfigurations.keySet()
									  .stream()
									  .map(id -> ClientConfigurationFactory.createClientConfiguration(rawClientConfigurations.get(id), id))
									  .collect(Collectors.toList());
	}

	private static Map<String, Map<String, String>> getRawClientConfigurations(Map<String, String> properties) {
		Map<String, Map<String, String>> rawClientConfigurations = new HashMap<>();
		properties.keySet().forEach(property -> {
			String clientId = property.replaceFirst("(?=[.]).*$", "");
			Map<String, String> rawClientConfiguration = rawClientConfigurations.get(clientId);
			if (null == rawClientConfiguration)
				rawClientConfiguration = new HashMap<>();
			rawClientConfiguration.put(property, properties.get(property));
			rawClientConfigurations.put(clientId, rawClientConfiguration);
		});
		return rawClientConfigurations;
	}

	public List<ClientConfiguration> getClientConfigurations() {
		Map<String, String> properties = getProperties();
		Map<String, Map<String, String>> rawClientConfigurations = getRawClientConfigurations(properties);
		return computeClientConfigurations(rawClientConfigurations);
	}

	private Map<String, String> getProperties() {
		MutablePropertySources propertySources = environment.getPropertySources();

		return StreamSupport.stream(propertySources.spliterator(), false)
							.filter(EnumerablePropertySource.class::isInstance)
							.map(ps -> ((EnumerablePropertySource<?>) ps).getPropertyNames())
							.flatMap(Arrays::stream)
							// remove all the properties that are not needed to this library
							.filter(property -> property.startsWith(WCSConstant.WCS))
							// collect used property to a map
							.collect(Collectors.toMap(property -> property.replaceFirst(WCSConstant.WCS + '.', ""),
													  property -> environment.getProperty(property, "")));
	}
}
