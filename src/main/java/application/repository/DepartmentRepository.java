package application.repository;

import application.models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepository
{
    private static JdbcTemplate jdbc;

    @Autowired
    public DepartmentRepository(JdbcTemplate template)
    {
        jdbc = template;
    }

    public List<Department> getAll()
    {
        return jdbc.query("SELECT * FROM department" , new BeanPropertyRowMapper<>(Department.class));
    }
    public Department getBy(String name)
    {
        return jdbc.query("SELECT * FROM department WHERE name=?" , new Object[]{name} , new BeanPropertyRowMapper<>(Department.class))
                .stream().findAny().orElse(null);
    }
    public void increment(String name)
    {
        jdbc.update("UPDATE department SET workers=workers + 1 WHERE name=?" , new Object[]{name});
    }
    public void insert(Department department)
    {
        jdbc.update("INSERT INTO department VALUES(?,?)" , department.getName() , department.getWorkers());
    }
    public void update(String name , Department department)
    {
        jdbc.update("UPDATE department SET name=?,workers=? WHERE name=?" ,
                department.getName() , department.getWorkers() , name);
    }
    public void delete(String name)
    {
        jdbc.update("DELETE FROM department WHERE name=?" , name);
    }
}