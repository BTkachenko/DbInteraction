package application.controllers;

import application.models.*;
import application.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/app")
public class RequestController
{
    private ApplicationService service;
    private User user;

    @Autowired
    RequestController(ApplicationService service , User user)
    {
        this.service = service;
        this.user = user;
        this.user.setLogin(null);
    }
    /*Worker-page request*/
    @PostMapping("/createTask")
    public String createTask(@ModelAttribute("task") Task task)
    {
        if (task.getTask().length() > 0)
        {
            service.tasks().insert(task);
        }
        return "redirect:/app/worker";
    }
    @PostMapping("/createOp")
    public String createOperation(@ModelAttribute("operation")
                                  Operation operation)
    {
        if (operation.getType().length() > 0 &&
        operation.getCost() > 0)
        {
            System.out.println(operation.getClient());
            service.operations().insert(operation);
        }
        return "redirect:/app/client";
    }
    @PostMapping("/createDep")
    public String createDepartment(@ModelAttribute("department")
                                   Department department)
    {
        if (department.getName().length() > 0 &&
        service.departments().getBy(department.getName()) == null)
        {
            service.departments().insert(department);
        }
        return "redirect:/app/admin";
    }
    @PostMapping("/wUp")
    public String workerUp(@ModelAttribute("worker")
                           Worker worker)
    {
        if (worker.getLogin().length() > 0 &&
        worker.getPassword().length() > 0 &&
        service.workers().getBy(worker.getLogin()) == null &&
        service.departments().getBy(worker.getDepartment()) != null &&
        worker.getName().length() > 0 &&
        worker.getSalary() > 0 &&
        worker.getSpecialty().length() > 0)
        {
            user.setIsIn(true);
            user.setStatus("WORKER");
            user.setLogin(worker.getLogin());
            user.setPassword(worker.getPassword());
            service.workers().insert(worker);
            service.departments().increment(worker.getDepartment());
            return "redirect:/app/worker";
        }
        return "redirect:/app/workerUp";
    }
    @PostMapping("/wIn")
    public String workerIn(@ModelAttribute("worker") Worker worker)
    {
        if (service.workers().getBy(worker.getLogin() , worker.getPassword()) != null)
        {
            user.setStatus("WORKER");
            user.setIsIn(true);
            user.setLogin(worker.getLogin());
            user.setPassword(worker.getPassword());
            return "redirect:/app/worker";
        }
        return "redirect:/app/workerIn";
    }
    @PostMapping("/cUp")
    public String clientUp(@ModelAttribute("client") Client client)
    {
        if (client.getLogin().length() > 0 &&
        client.getPassword().length() > 0 &&
        client.getBalance() >= 0 &&
        service.clients().getBy(client.getLogin() , client.getPassword()) == null)
        {
            user.setPassword(client.getPassword());
            user.setStatus("CLIENT");
            user.setLogin(client.getLogin());
            user.setIsIn(true);
            service.clients().insert(client);
            return "redirect:/app/client";
        }
        return "redirect:/app/clientUp";
    }
    @PostMapping("/cIn")
    public String clientIn(@ModelAttribute("client") Client client)
    {
        if (service.clients().getBy(client.getLogin() , client.getPassword()) != null)
        {
            user.setIsIn(true);
            user.setPassword(client.getPassword());
            user.setLogin(client.getLogin());
            user.setStatus("CLIENT");
            return "redirect:/app/client";
        }
        return "redirect:/app/clientIn";
    }
    @PostMapping("/aIn")
    public String adminIn(@ModelAttribute("admin") Client client)
    {
        if (client.getLogin().equals("ADMIN")
                && client.getPassword().equals("ADMIN"))
        {
            user.setStatus("ADMIN");
            user.setLogin(client.getLogin());
            user.setPassword(client.getPassword());
            user.setIsIn(true);
            return "redirect:/app/admin";
        }
        return "redirect:/app/adminIn";
    }
    @PatchMapping("/clientE")
    public String clientEdit(@ModelAttribute("client") Client client)
    {
        service.clients().update(client.getLogin() , client);
        user.setPassword(client.getPassword());
        return "redirect:/app/client";
    }
    @DeleteMapping("/deleteOp")
    public String deleteOperations(@ModelAttribute("operation") Operation operation)
    {
        service.operations().delete(operation);
        return "redirect:/app/client";
    }
    @PatchMapping("/workerE")
    public String workerEdit(@ModelAttribute("worker") Worker worker)
    {
        service.workers().update(worker.getLogin() , worker);
        user.setPassword(worker.getPassword());
        return "redirect:/app/worker";
    }
    @DeleteMapping("/deleteTasks")
    public String deleteTasks(@ModelAttribute("worker") Worker worker)
    {
        service.tasks().delete(worker.getLogin());
        return "redirect:/app/worker";
    }
}