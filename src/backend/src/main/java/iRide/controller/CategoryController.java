package iRide.controller;

import iRide.service.Category.CategoryService;
import iRide.service.Category.model.input.CategoryCreateInput;
import iRide.service.Category.model.output.CategoryListAdminOutput;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "")
    public String getInstructors(Model model){
        List<CategoryListAdminOutput> categoryListAdminOutputs = this.categoryService.getCategoryListAdminOutput();
        if (model.asMap().get("code") != null) {
            Integer code = (Integer)model.asMap().get("code");
            switch (code) {
                case 101:
                    model.addAttribute("infoMessage", "Kategoria została usunięty pomyślnie.");
                    break;
                case 201:
                    model.addAttribute("infoError", "Wybrana kategoria została już usunięta.");
                    break;
                case 202:
                    model.addAttribute("infoError", "Kategoria o wybranym id nie istnieje.");
                    break;
                default:
                    model.addAttribute("infoError", "Wystąpił nieznany błąd.");
            }
        }
        model.addAttribute("categoryListAdminOutputs", categoryListAdminOutputs);
        return "admin/categories";
    }


    @DeleteMapping(value = "/{id}")
    public String deleteCategory(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            this.categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("code", 101);
        } catch (NotFoundException e){
            redirectAttributes.addFlashAttribute("code", 201);
        }
        return "redirect:/category";
    }


    @PostMapping(value = "/")
    public ResponseEntity<String> createCategory(@RequestBody CategoryCreateInput categoryCreateInput) {
        int categoryId = categoryService.createCategory(categoryCreateInput);
        return ResponseEntity.ok("Category has been created. Category id = " + categoryId);

    }
}
