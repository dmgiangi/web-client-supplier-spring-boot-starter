package dev.dmgiangi.webclientsupplier;

import dev.dmgiangi.webclientsupplier.supplier.RemoteServiceSupplier;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
public class WebClientSupplierAutoConfiguration {
    @Bean
    public BeanDefinitionRegistryPostProcessor remoteServiceSupplier(ConfigurableEnvironment environment){
        return new RemoteServiceSupplier(environment);
    }
}
