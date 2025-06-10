package net.ddns.jazzsrv.kollektiv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.ddns.jazzsrv.kollektiv.entity.Task;
import net.ddns.jazzsrv.kollektiv.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}