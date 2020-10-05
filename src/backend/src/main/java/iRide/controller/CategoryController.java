package iRide.controller;

import iRide.service.Category.CategoryService;
import iRide.service.Category.model.input.CategoryCreateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //    TODO moze przejsc na modele zwracane w response entity?
    // TODO moze przejsc z exceptionow na -1
    @PostMapping(value = "/create")
    public ResponseEntity<String> createCategory(@RequestBody CategoryCreateInput categoryCreateInput) {
        if (!categoryCreateInput.checkDataCompleteness()) {
            return new ResponseEntity<>("Incomplete request data.", HttpStatus.BAD_REQUEST);
        }
        int categoryId = categoryService.createCategory(categoryCreateInput);
        return ResponseEntity.ok("Category has been created. Category id = " + categoryId);

    }
}
