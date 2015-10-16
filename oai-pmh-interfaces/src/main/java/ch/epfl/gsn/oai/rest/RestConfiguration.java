package ch.epfl.gsn.oai.rest;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by kryvych on 16/10/15.
 */
@Configuration
@ComponentScan("ch.epfl.gsn.oai.rest")
public class RestConfiguration {

    @Bean(name = "templateConfiguration")
    public PropertiesFactoryBean configuration() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("templates/template.properties"));
        return bean;
    }
}
