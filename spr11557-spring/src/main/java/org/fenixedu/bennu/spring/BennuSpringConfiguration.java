package org.fenixedu.bennu.spring;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

@Configuration
@ComponentScan("org.fenixedu.bennu")
public class BennuSpringConfiguration extends WebMvcConfigurationSupport {

    Logger logger = LoggerFactory.getLogger(BennuSpringConfiguration.class);

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setExposeContextBeansAsAttributes(true);
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(1_000_000);
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public MessageSource messageSource(ApplicationContext context) {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setDefaultEncoding("UTF-8");
        final String[] baseNames = getBaseNames(context).toArray(new String[0]);
        logger.debug("Adding basenames by @ComposedAnnotation configuration: {}", Arrays.toString(baseNames));
        source.setBasenames(baseNames);
        // Reload resources only when in development mode
        source.setCacheSeconds(1);
        return source;
    }

    private Set<String> getBaseNames(ApplicationContext context) {
        final Set<String> baseNames = new HashSet<>();
        baseNames.add(getBundleBasename("messages"));
        final String[] beanNames = context.getBeanNamesForAnnotation(ComposedAnnotation.class);
        for (String beanName : beanNames) {
            ComposedAnnotation bennuSpringModuleAnnotation = context.findAnnotationOnBean(beanName, ComposedAnnotation.class);
            if (bennuSpringModuleAnnotation != null) {
                final List<String> bundles = Arrays.asList(bennuSpringModuleAnnotation.bundles());
                baseNames.addAll(FluentIterable.from(bundles).transform(new Function<String, String>() {

                    @Override
                    public String apply(String bundle) {
                        return getBundleBasename(bundle);
                    }
                }).toSet());
            }
        }
        return baseNames;
    }

    private String getBundleBasename(String bundle) {
        return "/WEB-INF/resources/" + bundle;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

}
