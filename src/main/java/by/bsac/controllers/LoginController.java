package by.bsac.controllers;

import by.bsac.models.htmlForms.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/login", "/login.html"})
public class LoginController {

    //Logger
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    //Spring beans
    private LoginForm login_form;

    @ModelAttribute(name = "login_form")
    public LoginForm getLoginForm() {
        return this.login_form;
    }

    /**
     * Listen clients request on '/login' URL.
     * @return - name of view ('login.html");
     */
    @GetMapping
    public String getLoginPage() {
        //Return
        return "login";
    }

    @PostMapping
    public void signIn(@ModelAttribute LoginForm login_form) {

        //Log
        LOGGER.info(login_form.getUserEmail() +" in login controller post method");
    }


    //Spring autowiring
    @Autowired
    public void autowire(LoginForm a_login_form) {
        this.login_form = a_login_form;
    }

}
