package io.mrchenli;

import mrchenli.HttpMapperFactory;
import org.springframework.beans.factory.FactoryBean;

public class HttpMapperFactoryBean implements FactoryBean {

    private final Class<?> interfaceType;

    private final HttpMapperFactory httpMapperFactory;

    public HttpMapperFactoryBean(Class interfaceType,HttpMapperFactory httpMapperFactory){
        this.interfaceType = interfaceType;
        this.httpMapperFactory = httpMapperFactory;
    }

    @Override
    public Object getObject() throws Exception {
        return httpMapperFactory.getMapper(interfaceType);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
