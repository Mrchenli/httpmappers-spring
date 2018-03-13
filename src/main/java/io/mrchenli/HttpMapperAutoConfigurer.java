package io.mrchenli;

import mrchenli.HttpMapperFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class HttpMapperAutoConfigurer implements BeanDefinitionRegistryPostProcessor{
    private String[] configPathes;
    private String[] mapperLocations;

    public String[] getConfigPathes() {
        return configPathes;
    }

    public void setConfigPathes(String[] configPathes) {
        this.configPathes = configPathes;
    }

    public String[] getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        HttpMapperFactory httpMapperFactory = HttpMapperFactory.builder().configManager(configPathes).httpmapperConfig(mapperLocations).build();
        final HttpmapperScanner scanner = new HttpmapperScanner(registry,httpMapperFactory);
        scanner.registerDefaultFilters();
        scanner.doScan(mapperLocations);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
