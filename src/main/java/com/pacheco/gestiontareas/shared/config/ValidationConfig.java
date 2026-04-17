package com.pacheco.gestiontareas.shared.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuración de mensajes de validación personalizados.
 * Carga los mensajes desde el fichero errors.properties
 * para mostrar errores de validación en español.
 */
@Configuration
public class ValidationConfig {

    /**
     * Define el origen de los mensajes de error.
     * Se buscan en classpath:messages/errors.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages/errors");
        messageSource.setDefaultEncoding("ISO-8859-1");
        return messageSource;
    }

    /**
     * Conecta los mensajes personalizados con el sistema
     * de validación de Bean Validation.
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

}
