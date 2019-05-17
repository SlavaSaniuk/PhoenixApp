package by.bsac.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "by.bsac.controllers")
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
}
