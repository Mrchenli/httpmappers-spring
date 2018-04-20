package io.mrchenli;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HttpMapperScannerRegistrar.class)
public @interface HttpMapperScan {

    String mapperLocations() default "";

    String configPaths() default "";

}
