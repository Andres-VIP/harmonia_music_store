package com.harmonia.store.service;

import com.harmonia.store.model.Category;
import com.harmonia.store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Cacheable(value = "categories", key = "'all'")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Cacheable(value = "categories", key = "#id")
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Cacheable(value = "categories", key = "'name_' + #name")
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

    @CacheEvict(value = "categories", allEntries = true)
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @CacheEvict(value = "categories", key = "#category.id")
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @CacheEvict(value = "categories", allEntries = true)
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> searchCategoriesByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
} 