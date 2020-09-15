package iRide.service.InstructorCategory;

import iRide.model.Category;
import iRide.model.Instructor;
import iRide.model.InstructorCategory;
import iRide.repository.InstructorCategoryRepository;
import iRide.service.Category.CategoryService;
import iRide.service.Instructor.InstructorService;
import iRide.service.InstructorCategory.model.input.InstructorCategoryInput;
import iRide.service.Student.StudentService;
import iRide.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InstructorCategoryService {

    private final InstructorCategoryRepository instructorCategoryRepository;
    private final CategoryService categoryService;
    private final InstructorService instructorService;

    @Autowired
    public InstructorCategoryService(InstructorCategoryRepository instructorCategoryRepository, CategoryService categoryService, InstructorService instructorService){
        this.instructorCategoryRepository = instructorCategoryRepository;
        this.categoryService = categoryService;
        this.instructorService = instructorService;
    }

    public int assignCategoriesToInstructor(ArrayList<InstructorCategoryInput> instructorCategoryInputs, int instructorId) throws NotFoundException {
        Instructor instructor = instructorService.getInstructorById(instructorId);
        for (InstructorCategoryInput instructorCategoryInput : instructorCategoryInputs){
            int categoryId = categoryService.getCategoryId(instructorCategoryInput.getCategoryName(), instructorCategoryInput.getCategoryType());
            InstructorCategory instructorCategory = new InstructorCategory(instructor, categoryId);
            //TODO liczenie ile dodanych kategorii + walidacja
        }
    }

}