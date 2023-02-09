package application.repository;

import application.models.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkerRepository
{
    private static JdbcTemplate jdbc;

    @Autowired
    public WorkerRepository(JdbcTemplate template)
    {
        jdbc = template;
    }

    public List<Worker> getAll()
    {
        return jdbc.query("SELECT * FROM worker" , new BeanPropertyRowMapper<>(Worker.class));
    }
    public Worker getBy(String login , String password)
    {
        return jdbc.query("SELECT * FROM worker WHERE login=? AND password=md5(?)" , new Object[]{login , password} , new BeanPropertyRowMapper<>(Worker.class))
                .stream().findAny().orElse(null);
    }
    public Worker getBy(String login)
    {
        return jdbc.query("SELECT * FROM worker WHERE login=?" , new Object[]{login} , new BeanPropertyRowMapper<>(Worker.class))
                .stream().findAny().orElse(null);
    }
    public void insert(Worker worker)
    {
        jdbc.update("INSERT INTO worker VALUES(?,?,?,?,?,md5(?))" , worker.getDepartment()
        , worker.getName() , worker.getSalary() , worker.getSpecialty() , worker.getLogin() , worker.getPassword());
    }
    public void update(String login , Worker worker)
    {
        jdbc.update("UPDATE worker SET name=?,salary=?,specialty=?,password=md5(?) WHERE login=?" ,
        worker.getName() , worker.getSalary() , worker.getSpecialty() , worker.getPassword() , login);
    }
    public void delete(Worker worker)
    {
        jdbc.update("DELETE FROM worker WHERE login=?" , worker.getLogin());
        jdbc.update("SELECT clear_worker(?,?)" , worker.getDepartment() , worker.getLogin());
    }
}