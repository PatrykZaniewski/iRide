package iRide.service.Category.model.input;

public class CategoryCreateInput {
    private String categoryName;
    private String categoryType;

    public Boolean checkDataCompleteness(){
        return categoryName != null && categoryType != null;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
