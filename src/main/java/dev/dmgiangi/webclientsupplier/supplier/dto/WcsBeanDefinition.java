package dev.dmgiangi.webclientsupplier.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.support.AbstractBeanDefinition;

@Getter
@AllArgsConstructor
public class WcsBeanDefinition {
	private final String beanName;
	private final AbstractBeanDefinition beanDefinition;
}
