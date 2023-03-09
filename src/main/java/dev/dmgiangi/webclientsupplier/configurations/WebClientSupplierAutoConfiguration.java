package dev.dmgiangi.webclientsupplier.configurations;

import dev.dmgiangi.webclientsupplier.parser.ConfigurationParser;
import dev.dmgiangi.webclientsupplier.supplier.ClientBeanFactory;
import dev.dmgiangi.webclientsupplier.supplier.strategy.RestTemplateStrategy;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

import java.util.Collections;

public class WebClientSupplierAutoConfiguration {
    @Bean
    public RestTemplateStrategy restTemplateStrategy(RestTemplateBuilder builder) {
        return new RestTemplateStrategy(builder);
    }

    @Bean
    public ClientBeanFactory clientBeanFactory(RestTemplateStrategy restTemplateStrategy) {
        return new ClientBeanFactory(Collections.singletonList(restTemplateStrategy));
    }

    @Bean
    public ConfigurationParser remoteServiceSupplier(AbstractEnvironment environment) {
        return new ConfigurationParser(environment);
    }
}
