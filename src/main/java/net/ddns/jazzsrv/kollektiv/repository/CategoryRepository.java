package net.ddns.jazzsrv.kollektiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ddns.jazzsrv.kollektiv.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}