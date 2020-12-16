package iRide.service.Category.model.output;

import java.util.Map;

public class CategoryCreateOutput {
    private Map<String, String> categoryTypes;

    public Map<String, String> getCategoryTypes() {
        return categoryTypes;
    }

    public void setCategoryTypes(Map<String, String> categoryTypes) {
        this.categoryTypes = categoryTypes;
    }
}
