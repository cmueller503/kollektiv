package net.ddns.jazzsrv.kollektiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ddns.jazzsrv.kollektiv.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}