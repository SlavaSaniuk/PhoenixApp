package by.bsac.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ThymeleafFrameworkConfigurer implements ApplicationContextAware {

    private ApplicationContext application_context; //Phoenix application context;

    /**
     * Template resolver. Search and return template views under ''prefix' name 'suffix' folder.
     * {@link org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver} is preferred implementation of template resolver.
     * @return - Configured {@link org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver} resolver.
     */
    @Bean(name = "ThymeleafTemplateResolver")
    public SpringResourceTemplateResolver templateResolver() {

        //Default preferred resolver
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();

        //Application context
        resolver.setApplicationContext(this.application_context);
        //Prefix / suffix
        resolver.setPrefix("/WEB-INF/Views/");
        resolver.setSuffix(".html");
        //Disable cache
        resolver.setCacheable(false);
        //Encoding
        resolver.setCharacterEncoding("UTF-8");
        //HTML mode
        resolver.setTemplateMode(TemplateMode.HTML);
        //Order
        resolver.setOrder(2);

        //Return
        return resolver;
    }

    /**
     * Template engine. You must configure you template resolvers if that template engine.
     * And add your custom message source to that engine.
     * @return - Configured {@link org.thymeleaf.spring5.SpringTemplateEngine} engine.
     */
    @Bean(name = "ThymeleafTemplateEngine")
    public SpringTemplateEngine templateEngine() {
        //Default template engine
        SpringTemplateEngine engine = new SpringTemplateEngine();

        //Add template resolvers
        //Note if you have more than one templateResolver ->
        //You must enable its in that template engine.
        engine.addTemplateResolver(this.templateResolver());

        //Add message sources

        //Return
        return engine;
    }

    /**
     * View resolver. Implementation of Spring {@link org.springframework.web.servlet.ViewResolver} interface.
     * View resolver search views by its names, and render them.
     * @return - Configured {@link org.springframework.web.servlet.ViewResolver} resolver.
     */
    @Bean(name = "ThymeleafViewResolver")
    public ViewResolver thymeleafViewResolver() {
        //Create thymeleaf implementation of spring view resolver
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();

        //Set Spring template engine
        resolver.setTemplateEngine(this.templateEngine());

        //Return
        return resolver;
    }

    //Spring autowiring
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.application_context = applicationContext;
    }
}
