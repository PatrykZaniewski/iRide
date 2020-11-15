package iRide.controller;

import iRide.service.Category.CategoryService;
import iRide.service.Category.model.input.CategoryCreateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> createCategory(@RequestBody CategoryCreateInput categoryCreateInput) {
        int categoryId = categoryService.createCategory(categoryCreateInput);
        return ResponseEntity.ok("Category has been created. Category id = " + categoryId);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        this.categoryService.deleteById(id);
        return ResponseEntity.ok("");
    }
}
