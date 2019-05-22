package by.bsac.models.htmlForms;

import org.springframework.stereotype.Component;

@Component("login-form")
public class LoginForm {

    private String user_email;
    private String user_password;

    /*
        *Getters / Setters
     */

    public String getUserEmail() {
        return this.user_email;
    }

    public void setUserEmail(String a_user_email) {
        this.user_email = a_user_email;
    }

    public String getUserPassword() {
        return user_password;
    }

    public void setUserPassword(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
