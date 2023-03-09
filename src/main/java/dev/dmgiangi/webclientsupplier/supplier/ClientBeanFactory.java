package dev.dmgiangi.webclientsupplier.supplier;

import dev.dmgiangi.webclientsupplier.parser.dto.ClientConfiguration;
import dev.dmgiangi.webclientsupplier.supplier.dto.WcsBeanDefinition;
import dev.dmgiangi.webclientsupplier.supplier.strategy.BeanDefinitionStrategy;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.BeanCreationException;

import java.util.List;

@AllArgsConstructor
public class ClientBeanFactory {
	private final List<BeanDefinitionStrategy> beanDefinitionStrategies;

	public WcsBeanDefinition createClientBean(ClientConfiguration clientConfiguration) {
		return beanDefinitionStrategies.stream()
									   .filter(strategy -> strategy.canHandle(clientConfiguration.getType()))
									   .findAny()
									   .orElseThrow(() -> new BeanCreationException(""))
									   .getBeanDefinition(clientConfiguration);
	}
}
