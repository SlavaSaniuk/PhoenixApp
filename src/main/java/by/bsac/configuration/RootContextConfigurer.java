package by.bsac.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = by.bsac.models.HtmlFormsConfigurer.class)
public class RootContextConfigurer {

}
