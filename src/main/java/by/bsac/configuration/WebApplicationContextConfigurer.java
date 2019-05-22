package by.bsac.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"by.bsac.controllers"}) //Packet where live all application controllers
@Import(value = {by.bsac.configuration.ThymeleafFrameworkConfigurer.class})
public class WebApplicationContextConfigurer implements WebMvcConfigurer {

    /*
        Implements WebMvcConfigurer methods
     */

    /**
     * Configure simple automated controllers pre-configured with the response
     * status code and/or a view to render the response body. This is useful in
     * cases where there is no need for custom controller logic -- e.g. render a
     * home page, perform simple site URL redirects, return a 404 status with
     * HTML content, a 204 with no content, and more.
     * @param registry - view controllers registration.
     */
    public void addViewControllers(ViewControllerRegistry registry) {

        //Set redirect from "/" path to login page
        registry.addRedirectViewController("/", "/login");
    }

    /**
     * Configure a handler to delegate unhandled requests by forwarding to the
     * Servlet container's "default" servlet. A common use case for this is when
     * the {@link DispatcherServlet} is mapped to "/" thus overriding the
     * Servlet container's default handling of static resources.
     */
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of
     * controller method invocations. Interceptors can be registered to apply
     * to all requests or be limited to a subset of URL patterns.
     * <p><strong>Note</strong> that interceptors registered here only apply to
     * controllers and not to resource handler requests. To intercept requests for
     * static resources either declare a
     * {@link org.springframework.web.servlet.handler.MappedInterceptor MappedInterceptor}
     * bean or switch to advanced configuration mode by extending
     * {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
     * WebMvcConfigurationSupport} and then override {@code resourceHandlerMapping}.
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.localeChangeInterceptor());
    }

    /**
     * Mapping static resources such as styles, images to classpath resources.
     * When client want to load static resource his sent request, then Dispatcher servlet detect path to resource and load correct file.
     * @param registry - Static resources registry.
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //Add CSS folder
        registry.addResourceHandler("/styles/**").addResourceLocations("classpath:/static/styles/");
        //Add JavaScript libs folder
        registry.addResourceHandler("/libs/**").addResourceLocations("classpath:/static/libs/");
        //Add images folder
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        //Add fonts folder
        registry.addResourceHandler("/fonts/").addResourceLocations("classpath:/static/fonts/");

    }

    /*
        Localization section
     */

    /**
     * Implementation of locale resolver interface.
     * LocaleResolver use "Accept-Languages" request header to resolve locale.
     * @return - Configured {@link org.springframework.web.servlet.LocaleResolver} object.
     */
    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        //Create request locale resolver
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        //Default locale - USA
        resolver.setDefaultLocale(Locale.US);
        //Return
        return resolver;
    }

    /**
     * LocaleChangeInterceptor change user locale based on "primary-language" request header.
     * If you want to change user locale then set "primary-language" header to HttpServletRequest and specify desired value.
     * @return - Configured {@link org.springframework.web.servlet.i18n.LocaleChangeInterceptor} object.
     */
    @Bean(name = "localeChangeInterceptor")
    public LocaleChangeInterceptor localeChangeInterceptor() {
        //Create object
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();

        //Set trigger
        lci.setParamName("primary-language");

        //Return
        return lci;
    }


}
