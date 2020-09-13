package iRide.service.Category;

import iRide.repository.CategoryRepository;
import iRide.service.Category.model.input.CategoryCreateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public int createCategory(CategoryCreateInput categoryCreateInput){
        if (!categoryRepository.getCategoryByNameByType(categoryCreateInput.getCategoryName(), categoryCreateInput.getCategoryType()).isPresent()){
            //TODO zrobic wyjatek, zwracane modele
        }
    }
}
