package net.ddns.jazzsrv.kollektiv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ddns.jazzsrv.kollektiv.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
}