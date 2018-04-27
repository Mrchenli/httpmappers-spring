package io.mrchenli;

import mrchenli.HttpMapperFactory;
import org.springframework.context.annotation.Bean;

public class HttpMapperFactoryAutoConfiguration {

    public static HttpMapperFactory httpMapperFactory;

    @Bean
    public HttpMapperFactory httpMapperFactory() {
        return httpMapperFactory;
    }

}
