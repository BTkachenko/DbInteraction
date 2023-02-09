package application.service;

import application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService
{
    private WorkerRepository workerRepository;

    @Autowired
    public void setWorkerRepository(WorkerRepository workerRepository)
    {
        this.workerRepository = workerRepository;
    }

    public WorkerRepository workers()
    {
        return this.workerRepository;
    }

    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository)
    {
        this.taskRepository = taskRepository;
    }

    public TaskRepository tasks()
    {
        return this.taskRepository;
    }

    private OperationRepository operationRepository;

    @Autowired
    public void setOperationRepository(OperationRepository operationRepository)
    {
        this.operationRepository = operationRepository;
    }

    public OperationRepository operations()
    {
        return this.operationRepository;
    }

    private DepartmentRepository departmentRepository;

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository)
    {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentRepository departments()
    {
        return this.departmentRepository;
    }

    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository)
    {
        this.clientRepository = clientRepository;
    }

    public ClientRepository clients()
    {
        return this.clientRepository;
    }
}