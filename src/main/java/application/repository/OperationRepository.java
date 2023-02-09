package application.repository;

import application.models.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OperationRepository
{
    private static JdbcTemplate jdbc;

    @Autowired
    public OperationRepository(JdbcTemplate template)
    {
        jdbc = template;
    }

    public List<Operation> getAll()
    {
        return jdbc.query("SELECT * FROM operations" , new BeanPropertyRowMapper<>(Operation.class));
    }
    public Operation getBy(String type)
    {
        return jdbc.query("SELECT * FROM operations WHERE type=?" , new Object[]{type} , new BeanPropertyRowMapper<>(Operation.class))
                .stream().findAny().orElse(null);
    }
    public List<Operation> getByClient(String login)
    {
        return jdbc.query("SELECT * FROM operations WHERE client=?" , new Object[]{login} , new BeanPropertyRowMapper<>(Operation.class));
    }
    public void insert(Operation operation)
    {
        jdbc.update("INSERT INTO operations VALUES(?,?,?)" ,
                operation.getType() , operation.getCost() , operation.getClient());
    }
    public void update(String type , Operation operation)
    {
        jdbc.update("UPDATE operations SET cost=?,client=? WHERE type=?" ,
                operation.getCost() , operation.getClient() , type);
    }
    public void delete(String type)
    {
        jdbc.update("DELETE FROM operations WHERE type=?" , type);
    }
    public void delete(Operation operation)
    {
        jdbc.update("DELETE FROM operations WHERE client=?" , operation.getClient());
    }
}