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
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public int createCategory(CategoryCreateInput categoryCreateInput) throws CategoryExistsException, NotFoundException {
        //TODO ta funkcja z dolu wyrzuca exception w tej u gory
        if (getCategoryByNameAndType(categoryCreateInput.getCategoryName(), categoryCreateInput.getCategoryType()) != -1){
            throw new CategoryExistsException("Category combination of " + categoryCreateInput.getCategoryName() + " and " + categoryCreateInput.getCategoryType() + " already exists");
        }
        return categoryRepository.save(new Category(categoryCreateInput)).getId();
    }

    public int getCategoryByNameAndType(String categoryName, String categoryType) throws NotFoundException {
        if (!categoryRepository.getCategoryByNameByType(categoryName, categoryType).isPresent())
        {
            throw new NotFoundException("Category combination of " +  categoryName + " and " + categoryType + " has not been found");
        }
        return categoryRepository.getCategoryByNameByType(categoryName, categoryType).get();
    }
}
