package dev.dmgiangi.webclientsupplier.supplier.strategy;

import dev.dmgiangi.webclientsupplier.WCSConstant;
import dev.dmgiangi.webclientsupplier.parser.dto.ClientConfiguration;
import dev.dmgiangi.webclientsupplier.parser.dto.enumerations.ClientType;
import dev.dmgiangi.webclientsupplier.supplier.dto.WcsBeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface BeanDefinitionStrategy {
	Logger LOGGER = LoggerFactory.getLogger(WCSConstant.BEAN_FACTORY_LOGGER_NAME);

	WcsBeanDefinition getBeanDefinition(ClientConfiguration clientConfiguration);

	boolean canHandle(ClientType clientType);
}
