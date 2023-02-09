package application.repository;

import application.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepository
{
    private static JdbcTemplate jdbc;

    @Autowired
    public ClientRepository(JdbcTemplate template)
    {
        jdbc = template;
    }

    public List<Client> getAll()
    {
        return jdbc.query("SELECT * FROM client" , new BeanPropertyRowMapper<>(Client.class));
    }
    public Client getBy(String login , String password)
    {
        return jdbc.query("SELECT * FROM client WHERE login=? AND password=md5(?)" , new Object[]{login , password} , new BeanPropertyRowMapper<>(Client.class))
                .stream().findAny().orElse(null);
    }
    public void insert(Client client)
    {
        jdbc.update("INSERT INTO client VALUES(?,md5(?),?)" , client.getLogin() , client.getPassword() , client.getBalance());
    }
    public void update(String login , Client client)
    {
        jdbc.update("UPDATE client SET password=md5(?),balance=? WHERE login=?" , client.getPassword() , client.getBalance() , login);
    }
    public void delete(String login)
    {
        jdbc.update("DELETE FROM client WHERE login=?" , login);
        jdbc.update("SELECT clear_client(?)" , login);
    }
}