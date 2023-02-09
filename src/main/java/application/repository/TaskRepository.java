package application.repository;

import application.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository
{
    private static JdbcTemplate jdbc;

    @Autowired
    public TaskRepository(JdbcTemplate template)
    {
        jdbc = template;
    }

    public List<Task> getAll()
    {
        return jdbc.query("SELECT * FROM tasks" , new BeanPropertyRowMapper<>(Task.class));
    }
    public Task getBy(String login , String task)
    {
        return jdbc.query("SELECT * FROM tasks WHERE worker=? AND task=?" , new Object[]{login , task} , new BeanPropertyRowMapper<>(Task.class))
                .stream().findAny().orElse(null);
    }
    public List<Task> getByWorker(String login)
    {
        return jdbc.query("SELECT * FROM tasks WHERE worker=?" , new Object[]{login} , new BeanPropertyRowMapper<>(Task.class));
    }
    public void insert(Task task)
    {
        jdbc.update("INSERT INTO tasks VALUES(?,?)" , task.getWorker() , task.getTask());
    }
    public void update(String login , String t , Task task)
    {
        jdbc.update("UPDATE tasks SET worker=?,task=? WHERE worker=? AND task=?" ,
                task.getWorker() , task.getTask() , login , t);
    }
    public void delete(String login , String task)
    {
        jdbc.update("DELETE FROM tasks WHERE worker=? AND task=?" , login , task);
    }
    public void delete(String login)
    {
        jdbc.update("DELETE FROM tasks WHERE worker=?" , login);
    }
}