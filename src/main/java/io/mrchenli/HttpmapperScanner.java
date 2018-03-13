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
@ThirdMapper
public class HttpmapperScanner extends ClassPathBeanDefinitionScanner{


    private HttpMapperFactory httpMapperFactory;

    public HttpmapperScanner(BeanDefinitionRegistry registry,HttpMapperFactory httpMapperFactory) {
        super(registry);
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
                    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(1,httpMapperFactory);
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

    public static void main(String[] args) {
        ThirdMapper thirdMapper = HttpmapperScanner.class.getAnnotation(ThirdMapper.class);
        String name= thirdMapper.annotationType().getName();
        System.out.println("name is ==>"+name);
    }

}
