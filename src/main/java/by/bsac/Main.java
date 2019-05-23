package by.bsac;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


public class Main implements WebApplicationInitializer {

    /**
     * Phoenix web application startup method.
     * @param servletContext - Servlet context.
     * @throws ServletException - If servlet context is null.
     */
    public void onStartup(ServletContext servletContext) throws ServletException {

        //Create root application context
        AnnotationConfigWebApplicationContext root_ctx = new AnnotationConfigWebApplicationContext();

        //Register root configuration class
        root_ctx.register(by.bsac.configuration.RootContextConfigurer.class);

        //Register root context with context loader listener
        //and associate hiw with servlet context
        servletContext.addListener(new ContextLoaderListener(root_ctx));

        root_ctx.getEnvironment().setActiveProfiles("Production");

        //Create web application context
        AnnotationConfigWebApplicationContext web_ctx = new AnnotationConfigWebApplicationContext();

        //Register web context configuration class
        web_ctx.register(by.bsac.configuration.WebApplicationContextConfigurer.class);

        //Associate servlet context with that web context
        web_ctx.setServletContext(servletContext);

        //Set root context as parent of web context
        web_ctx.setParent(root_ctx);

        //Register dispatcher servlet
        ServletRegistration.Dynamic dispatcher_servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(web_ctx));
        dispatcher_servlet.addMapping("/");
        dispatcher_servlet.setLoadOnStartup(1);

    }

}
