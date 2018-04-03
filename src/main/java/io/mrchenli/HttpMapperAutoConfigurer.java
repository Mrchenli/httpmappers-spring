package io.mrchenli;

import mrchenli.HttpMapperFactory;
import mrchenli.propertiesconfig.HttpMapperPropertiesUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class HttpMapperAutoConfigurer implements BeanDefinitionRegistryPostProcessor{
    private String configPath;
    private String mapperLocation;

    public HttpMapperAutoConfigurer() {
        HttpMapperPropertiesUtil httpMapperPropertiesUtil
                =new HttpMapperPropertiesUtil("application.properties");
        this.configPath = httpMapperPropertiesUtil.getValue("httpmapper.configPath");
        this.mapperLocation =  httpMapperPropertiesUtil.getValue("httpmapper.mapperLocation");
    }
    public String[] getConfigPathes() {
        return configPath.split(",");
    }

    public void setConfigPathes(String configPathes) {
        this.configPath = configPathes;
    }

    public String[] getMapperLocations() {
        return mapperLocation.split(",");
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocation = mapperLocations;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        HttpMapperFactory httpMapperFactory = HttpMapperFactory.builder().configManager(getConfigPathes()).httpmapperConfig(getMapperLocations()).build();
        final HttpmapperScanner scanner = new HttpmapperScanner(registry,httpMapperFactory);
        scanner.registerDefaultFilters();
        scanner.doScan(getMapperLocations());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
