package pl.wb.security.utils;

import javax.enterprise.context.RequestScoped;
import java.io.Serializable;

@RequestScoped
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 3366773769459132820L;
    private String email;

    public UserInfo() {
    }

    public UserInfo(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
