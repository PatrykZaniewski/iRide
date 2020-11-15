package iRide.service.InstructorCategory;

import iRide.model.Category;
import iRide.model.Instructor;
import iRide.model.InstructorCategory;
import iRide.repository.InstructorCategoryRepository;
import iRide.service.Category.CategoryService;
import iRide.service.Instructor.InstructorService;
import iRide.service.InstructorCategory.model.input.InstructorCategoryInput;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InstructorCategoryService {

    private final InstructorCategoryRepository instructorCategoryRepository;
    private final CategoryService categoryService;
    private final InstructorService instructorService;

    @Autowired
    public InstructorCategoryService(InstructorCategoryRepository instructorCategoryRepository, CategoryService categoryService, @Lazy InstructorService instructorService) {
        this.instructorCategoryRepository = instructorCategoryRepository;
        this.categoryService = categoryService;
        this.instructorService = instructorService;
    }

    @Transactional
    public int assignCategoriesToInstructor(List<InstructorCategoryInput> instructorCategoryInputs, int instructorId) {
        List<Integer> existingInstructorCategories = this.instructorCategoryRepository.getIdsOfInstructorCategories(instructorId).isPresent() ? this.instructorCategoryRepository.getIdsOfInstructorCategories(instructorId).get() : Collections.emptyList();
        Instructor instructor = null;
        List<InstructorCategory> instructorCategories = new ArrayList<>();
        try {
            instructor = instructorService.getOne(instructorId);
            for (InstructorCategoryInput instructorCategoryInput : instructorCategoryInputs) {
                Category category = categoryService.getOne(instructorCategoryInput.getCategoryId());
                if (!existingInstructorCategories.contains(category.getId())) {
                    InstructorCategory instructorCategory = new InstructorCategory(instructor, category);
                    instructorCategories.add(instructorCategory);
                    //TODO zbierac info o istniejacych i dawac exception jesli nie istnieje id
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
            return -1;
        }
        this.instructorCategoryRepository.deleteInstructorCategories(instructorId);
        for (InstructorCategory instructorCategory : instructorCategories) {
            this.instructorCategoryRepository.save(instructorCategory);
        }
        return 1;
    }

}