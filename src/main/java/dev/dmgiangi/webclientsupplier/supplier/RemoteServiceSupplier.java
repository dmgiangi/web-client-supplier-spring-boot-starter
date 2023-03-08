package dev.dmgiangi.webclientsupplier.supplier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

public class RemoteServiceSupplier implements BeanDefinitionRegistryPostProcessor {
    private final ConfigurableEnvironment environment;

    public RemoteServiceSupplier(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        AbstractBeanDefinition bd = BeanDefinitionBuilder.genericBeanDefinition(String.class, () -> "sgf").getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("myBean", bd);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
