package iRide.service.Category;

import iRide.model.Category;
import iRide.repository.CategoryRepository;
import iRide.service.Category.model.input.CategoryCreateInput;
import iRide.utils.exceptions.CategoryExistsException;
import iRide.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public int createCategory(CategoryCreateInput categoryCreateInput) {
        //TODO ta funkcja z dolu wyrzuca exception w tej u gory
        if (getCategoryByNameAndType(categoryCreateInput.getCategoryName(), categoryCreateInput.getCategoryType()) == null) {
            return -1;
        }
        return categoryRepository.save(new Category(categoryCreateInput)).getId();
    }

    public Category getCategoryByNameAndType(String categoryName, String categoryType) {
        if (!categoryRepository.getCategoryByNameByType(categoryName, categoryType).isPresent()) {
            return null;
        }
        return categoryRepository.getCategoryByNameByType(categoryName, categoryType).get();
    }

}
