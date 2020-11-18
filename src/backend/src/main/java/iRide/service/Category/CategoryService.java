package iRide.service.Category;

import iRide.model.Category;
import iRide.repository.CategoryRepository;
import iRide.repository.InstructorCategoryRepository;
import iRide.service.Category.model.input.CategoryCreateInput;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final InstructorCategoryRepository instructorCategoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, InstructorCategoryRepository instructorCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.instructorCategoryRepository = instructorCategoryRepository;
    }

    public int createCategory(CategoryCreateInput categoryCreateInput) {
        if (getCategoryByNameAndType(categoryCreateInput.getCategoryName(), categoryCreateInput.getCategoryType()) == null) {
            return categoryRepository.save(new Category(categoryCreateInput)).getId();
        }
        return -1;
    }

    public Category getCategoryByNameAndType(String categoryName, String categoryType) {
        if (this.categoryRepository.getCategoryByNameByType(categoryName, categoryType).isPresent()) {
            return this.categoryRepository.getCategoryByNameByType(categoryName, categoryType).get();
        }
        return null;
    }

    public Category getCategory(int categoryId) {
        Optional<Category> result = this.categoryRepository.findById(categoryId);
        if (!result.isPresent()) {
            throw new NotFoundException("Category with id = " + categoryId + " has not been found");
        }
        return result.get();
    }

    public void deleteById(int id) {
        this.categoryRepository.deleteById(id);
    }

}
