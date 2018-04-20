package io.mrchenli;

import mrchenli.HttpMapperFactory;
import mrchenli.request.param.ThirdMapper;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Arrays;
import java.util.Set;

public class HttpmapperScanner extends ClassPathBeanDefinitionScanner{


    private String[] mapperLocations;
    private String[] configPaths;

    public String[] getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String[] mapperLocation) {
        this.mapperLocations = mapperLocation;
    }

    public String[] getConfigPaths() {
        return configPaths;
    }

    public void setConfigPaths(String[] configPath) {
        this.configPaths = configPath;
    }

    public HttpmapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }



    private HttpMapperFactory httpMapperFactory;

    public HttpMapperFactory getHttpMapperFactory() {
        if(httpMapperFactory==null){
            httpMapperFactory = HttpMapperFactory
                    .builder()
                    .configManager(getConfigPaths())
                    .httpmapperConfig(getMapperLocations())
                    .build();
        }
        return httpMapperFactory;
    }

    public void setHttpMapperFactory(HttpMapperFactory httpMapperFactory) {
        this.httpMapperFactory = httpMapperFactory;
    }

    @Override
    protected void registerDefaultFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(ThirdMapper.class));
    }



    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        try {
            Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
            if(beanDefinitionHolders.isEmpty()){
                logger.warn("No Http mapper was found in '"+ Arrays.toString(basePackages)+"' package. Please check your configuration.");
            }else{
                for (BeanDefinitionHolder holder: beanDefinitionHolders){
                    GenericBeanDefinition beanDefinition = (GenericBeanDefinition) holder.getBeanDefinition();
                    String name = beanDefinition.getBeanClassName();
                    Class<?> clzz = Class.forName(name);
                    beanDefinition.setBeanClass(HttpMapperFactoryBean.class);
                    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0,clzz);
                    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(1,getHttpMapperFactory());
                }
            }
            return beanDefinitionHolders;
        }catch (Exception e){
            logger.error(e);
        }
        return null;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }


}
