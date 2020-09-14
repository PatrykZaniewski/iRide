package iRide.service.Category;

import iRide.model.Category;
import iRide.repository.CategoryRepository;
import iRide.service.Category.model.input.CategoryCreateInput;
import iRide.utils.exceptions.CategoryExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public int createCategory(CategoryCreateInput categoryCreateInput) throws CategoryExistsException {
        if (getCategoryId(categoryCreateInput.getCategoryName(), categoryCreateInput.getCategoryType()) != -1){
            throw new CategoryExistsException("Combination of " + categoryCreateInput.getCategoryName() + " and " + categoryCreateInput.getCategoryType() + " already exists");
        }
        return categoryRepository.save(new Category(categoryCreateInput)).getId();
    }

    public int getCategoryId(String categoryName, String categoryType){
        if (categoryRepository.getCategoryIdByNameByType(categoryName, categoryType).isPresent())
        {
            return categoryRepository.getCategoryIdByNameByType(categoryName, categoryType).get();
        }
        return -1;
    }
}
