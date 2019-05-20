package by.bsac.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = {"/login", "/login.html"})
public class LoginController {

    //Logger
    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public String getLoginPage(HttpServletResponse response) {

        //Return
        return "login";
    }

}
