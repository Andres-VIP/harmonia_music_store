package com.harmonia.store.controller;

import com.harmonia.store.model.Category;
import com.harmonia.store.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categories", description = "API for managing instrument categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    @Operation(summary = "Get all categories",
            description = "Returns a list of all available categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories obtained successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID",
            description = "Returns a specific category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<Category> getCategoryById(
            @Parameter(description = "Category ID") @PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get category by name",
            description = "Returns a specific category by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<Category> getCategoryByName(
            @Parameter(description = "Category name") @PathVariable String name) {
        return categoryService.getCategoryByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    @Operation(summary = "Create new category",
            description = "Creates a new category in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<Category> createCategory(
            @Parameter(description = "Category data") @Valid @RequestBody Category category) {
        Category created = categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category",
            description = "Updates an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<Category> updateCategory(
            @Parameter(description = "Category ID") @PathVariable Long id,
            @Parameter(description = "Updated category data") @Valid @RequestBody Category category) {
        category.setId(id);
        Category updated = categoryService.updateCategory(category);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category",
            description = "Deletes a category from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "Category ID") @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search categories by name",
            description = "Searches for categories containing the specified name")
    public ResponseEntity<List<Category>> searchCategories(
            @Parameter(description = "Name to search") @RequestParam String name) {
        List<Category> results = categoryService.searchCategoriesByName(name);
        return ResponseEntity.ok(results);
    }
} 