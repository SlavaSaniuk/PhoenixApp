package by.bsac.configuration;

import by.bsac.models.HtmlFormsBeans;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {HtmlFormsBeans.class,
        by.bsac.configuration.PersistenceConfiguration.class,
        by.bsac.services.ServicesConfiguration.class})
public class RootContextConfigurer {


}
