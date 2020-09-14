package iRide.service.InstructorCategory;

import iRide.model.Category;
import iRide.repository.InstructorCategoryRepository;
import iRide.service.Category.CategoryService;
import iRide.service.InstructorCategory.model.input.InstructorCategoryInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InstructorCategoryService {

    private final InstructorCategoryRepository instructorCategoryRepository;
    private final CategoryService categoryService;

    @Autowired
    public InstructorCategoryService(InstructorCategoryRepository instructorCategoryRepository, CategoryService categoryService){
        this.instructorCategoryRepository = instructorCategoryRepository;
        this.categoryService = categoryService;
    }

    public int assignCategoriesToInstructor(ArrayList<InstructorCategoryInput> instructorCategoryInputs){
        for (InstructorCategoryInput instructorCategoryInput : instructorCategoryInputs){
            //TODO liczenie ile dodanych kategorii + walidacja
        }
    }

}