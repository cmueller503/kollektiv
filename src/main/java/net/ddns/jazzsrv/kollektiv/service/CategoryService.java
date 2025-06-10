package net.ddns.jazzsrv.kollektiv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.ddns.jazzsrv.kollektiv.entity.Category;
import net.ddns.jazzsrv.kollektiv.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}