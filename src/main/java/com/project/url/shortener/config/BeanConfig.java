package com.project.url.shortener.config;

import io.seruco.encoding.base62.Base62;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UrlValidator getUrlValidator(){
        String[] schemes = {"http", "https"};
        return new UrlValidator(schemes);
    }

    @Bean
    public Base62 getBase62(){
        return Base62.createInstance();
    }
}
