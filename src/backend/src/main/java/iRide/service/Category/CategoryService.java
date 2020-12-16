package iRide.service.Category;

import iRide.model.Category;
import iRide.model.Course;
import iRide.model.Instructor;
import iRide.model.Student;
import iRide.repository.CategoryRepository;
import iRide.repository.InstructorCategoryRepository;
import iRide.service.Category.model.input.CategoryCreateInput;
import iRide.service.Category.model.output.CategoryCreateOutput;
import iRide.service.Category.model.output.CategoryListAdminOutput;
import iRide.service.Instructor.InstructorService;
import iRide.service.Student.StudentService;
import iRide.service.User.UserService;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final InstructorCategoryRepository instructorCategoryRepository;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final UserService userService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, InstructorCategoryRepository instructorCategoryRepository, @Lazy InstructorService instructorService, StudentService studentService, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.instructorCategoryRepository = instructorCategoryRepository;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.userService = userService;
    }

    public void createCategory(CategoryCreateInput categoryCreateInput) {
        if (this.getCategoryByNameAndType(categoryCreateInput.getCategoryName(), categoryCreateInput.getCategoryType()) != null) {
            throw new DataExistsException("Such category already exists.");
        }
        categoryRepository.save(new Category(categoryCreateInput));
    }

    public List<String> getCategoriesAsStringList(){
        List<String> result = new ArrayList<>();
        for(Category category: this.categoryRepository.findAll()){
            if(category.getCategoryType().equals("THEORY")){
                result.add(category.getCategoryName());
            }
        }
        return result;
    }

    public CategoryCreateOutput getCreateCategory(){
        CategoryCreateOutput categoryCreateOutput = new CategoryCreateOutput();

        Map<String, String> categoryTypes = new HashMap<>();
        categoryTypes.put("THEORY", mapParameters("THEORY"));
        categoryTypes.put("PRACTICE", mapParameters("PRACTICE"));

        categoryCreateOutput.setCategoryTypes(categoryTypes);

        return categoryCreateOutput;
    }

    public Map<Integer, String> getCategoriesForVehicles(){
        Map<Integer, String> result = new HashMap<>();
        for(Category category: this.categoryRepository.findAll()){
            if(category.getCategoryType().equals("PRACTICE")){
                result.put(category.getId(), category.getCategoryName());
            }
        }

        return result;
    }

    public List<String> getCategoriesByGroupAndId(int userId){
        Set<String> categories = new HashSet<>();
        String group = this.userService.getUser(userId).getAccountRole();

        switch (group){
            case "INSTRUCTOR":
                Instructor instructor = this.instructorService.getInstructorByUserId(userId);
                for (Course course: instructor.getCourses()){
                    categories.add(course.getCategory().getCategoryName());
                }
                break;
            case "STUDENT":
                Student student = this.studentService.getStudentByUserId(userId);
                for (Course course: student.getCourses()){
                    categories.add(course.getCategory().getCategoryName());
                }
                break;
            case "ADMIN":
                return getCategoriesAsStringList();
        }
        return new ArrayList<>(categories);
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

    public List<Category> getAllCategories(){
        return this.categoryRepository.findAll();
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
