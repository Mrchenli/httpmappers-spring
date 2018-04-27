package io.mrchenli;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

public class HttpMapperScannerRegistrar implements ImportBeanDefinitionRegistrar {

    private ResourceLoader resourceLoader;

    private Environment environment;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(HttpMapperScan.class.getName()));
        HttpmapperScanner scanner = new HttpmapperScanner(registry);
        scanner.setMapperLocations(annoAttrs.getStringArray("mapperLocations"));
        scanner.setConfigPaths(annoAttrs.getStringArray("configPaths"));
        scanner.registerDefaultFilters();
        scanner.doScan(annoAttrs.getStringArray("mapperLocations"));
    }

}
