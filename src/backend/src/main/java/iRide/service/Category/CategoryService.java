package iRide.service.Category;

import iRide.model.Category;
import iRide.repository.CategoryRepository;
import iRide.repository.InstructorCategoryRepository;
import iRide.service.Category.model.input.CategoryCreateInput;
import iRide.service.Category.model.output.CategoryListAdminOutput;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public Map<Integer, String> getCategoriesAsStringList(){
        Map<Integer, String> result = new HashMap<>();
        for(Category category: this.categoryRepository.findAll()){
            if(category.getCategoryType().equals("THEORY")){
                result.put(category.getId(), category.getCategoryName());
            }
        }
        return result;
    }

    public List<CategoryListAdminOutput> getCategoryListAdminOutput() {
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryListAdminOutput> categoryListAdminOutputs = new ArrayList<>();

        for (Category category : categories) {
            CategoryListAdminOutput categoryListAdminOutput = new CategoryListAdminOutput();

            categoryListAdminOutput.setId(category.getId());
            categoryListAdminOutput.setCategoryName(category.getCategoryName());
            categoryListAdminOutput.setCategoryType(mapParameters(category.getCategoryType()));

            categoryListAdminOutputs.add(categoryListAdminOutput);
        }
        return categoryListAdminOutputs;
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

    @Transactional
    public int deleteById(int id) {
        Category category = getCategory(id);
        this.categoryRepository.delete(category);
        return id;
    }

    private String mapParameters(String param){
        switch (param){
            case "THEORY":
                return "Teoria";
            case "PRACTICE":
                return "Praktyka";
            case "IN_PROGRESS":
                return "W trakcie";
            case "FINISHED":
                return "Ukończony";
            case "CANCELLED":
                return "Przerwany";
            case "WAITING":
                return "Oczekuje na rozpoczęcie";
            default:
                return "Status nieznany";
        }
    }

}
