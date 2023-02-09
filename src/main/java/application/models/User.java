package application.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class User
{
    User()
    {
        isIn = false;
        login = "";
        password = "";
        status = "NONE";
    }
    private Boolean isIn;
    private String login;
    private String password;
    private String status;
}