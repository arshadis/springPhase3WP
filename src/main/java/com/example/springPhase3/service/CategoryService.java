package com.example.springPhase3.service;

import com.example.springPhase3.model.Category;
import com.example.springPhase3.dto.CategoryResponseDTO;

import com.example.springPhase3.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void addCategory(String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            throw new IllegalArgumentException("Category already exists");
        }

        Category category = new Category();
        category.setName(categoryName);
        categoryRepository.save(category);
    }

    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryResponseDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
}
