package net.ddns.jazzsrv.kollektiv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.ddns.jazzsrv.kollektiv.entity.Project;
import net.ddns.jazzsrv.kollektiv.repository.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}