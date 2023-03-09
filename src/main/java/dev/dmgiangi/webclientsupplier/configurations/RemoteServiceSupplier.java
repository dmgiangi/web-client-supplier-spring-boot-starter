package dev.dmgiangi.webclientsupplier.configurations;

import dev.dmgiangi.webclientsupplier.WCSConstant;
import dev.dmgiangi.webclientsupplier.parser.ConfigurationParser;
import dev.dmgiangi.webclientsupplier.supplier.ClientBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class RemoteServiceSupplier implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(WCSConstant.PARSER_LOGGER_NAME);

    private ConfigurationParser parser;
    private ClientBeanFactory clientBeanFactory;
    private long initialTimeStamp;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        parser.getClientConfigurations()
              .stream()
              .map(clientBeanFactory::createClientBean)
              .forEach(bd -> registry.registerBeanDefinition(bd.getBeanName(), bd.getBeanDefinition()));

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("the initialization of the web clients took {} milliseconds", System.currentTimeMillis() - initialTimeStamp);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // Unused method
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initialTimeStamp = System.currentTimeMillis();
        parser = applicationContext.getBean(ConfigurationParser.class);
        clientBeanFactory = applicationContext.getBean(ClientBeanFactory.class);
    }
}
