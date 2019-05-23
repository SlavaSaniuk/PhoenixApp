package by.bsac.configuration;

import by.bsac.models.HtmlFormsBeans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@Configuration
@Import(value = {HtmlFormsBeans.class,
                                by.bsac.configuration.PersistenceConfiguration.class})
@PropertySource(value = "classpath:application.properties")
public class RootContextConfigurer {

    //Spring beans
    private Environment spring_environment;


    //Spring autowiring
    @Autowired
    public void autowire(Environment a_environment) {
        this.spring_environment = a_environment;
    }
}
