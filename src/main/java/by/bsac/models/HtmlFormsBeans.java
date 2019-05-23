package by.bsac.models;

import by.bsac.models.htmlForms.LoginForm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration(value = "html-forms")
public class HtmlFormsBeans {

    /**
     * Bean indicate object representation of login form.
     * Used for mapping between html form inputs and objects representation of it.
     * @return - {@link by.bsac.models.htmlForms.LoginForm} object.
     */
    @Bean(name = "login_form")
    @Description("Bean indicate object representation of login form.")
    public LoginForm getLoginForm() {
        return new LoginForm();
    }

}
