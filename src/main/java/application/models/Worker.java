package application.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Worker
{
    private String department;
    private String name;
    private Integer salary;
    private String specialty;
    private String login;
    private String password;
}