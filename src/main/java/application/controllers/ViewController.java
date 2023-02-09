package application.controllers;

import application.models.*;
import application.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class ViewController
{
    private ApplicationService service;
    private User user;

    @Autowired
    ViewController(ApplicationService service , User user)
    {
        this.service = service;
        this.user = user;
    }
    @GetMapping("/home")
    public String home(Model model)
    {
        model.addAttribute("user" , user);
        return "home";
    }

    @GetMapping("/client")
    public String client(Model model)
    {
        model.addAttribute("client" , service.clients().getBy(
                user.getLogin() , user.getPassword()
        ));
        model.addAttribute("operation" , new Operation());
        model.addAttribute("operations" , service.operations().getByClient(user.getLogin()));
        return "client";
    }
    @GetMapping("/worker")
    public String worker(Model model)
    {
        model.addAttribute("worker" , service.workers().getBy(
                user.getLogin() , user.getPassword()
        ));
        model.addAttribute("task" , new Task());
        model.addAttribute("tasks" , service.tasks().getByWorker(user.getLogin()));
        return "worker";
    }
    @GetMapping("/admin")
    public String admin(Model model)
    {
        model.addAttribute("admin" , user);
        model.addAttribute("department" , new Department());
        model.addAttribute("departments" , service.departments().getAll());
        return "admin";
    }
    @GetMapping("/workerIn")
    public String workerIn(Model model)
    {
        model.addAttribute("worker" , new Worker());
        return "sign/workerIn";
    }
    @GetMapping("/clientIn")
    public String clientIn(Model model)
    {
        model.addAttribute("client" , new Client());
        return "sign/clientIn";
    }
    @GetMapping("/adminIn")
    public String adminIn(Model model)
    {
        model.addAttribute("admin" , new Client());
        return "sign/adminIn";
    }
    @GetMapping("/clientUp")
    public String clientUp(Model model)
    {
        model.addAttribute("client" , new Client());
        return "sign/clientUp";
    }
    @GetMapping("/workerUp")
    public String workerUp(Model model)
    {
        model.addAttribute("worker" , new Worker());
        return "sign/workerUp";
    }
    @GetMapping("/out")
    public String out()
    {
        user.setLogin("NONE");
        user.setPassword("NONE");
        user.setStatus("NONE");
        user.setIsIn(false);
        return "redirect:/app/home";
    }
}