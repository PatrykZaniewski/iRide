package iRide.model;

import iRide.service.Category.model.input.CategoryCreateInput;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @NotNull
    @Column(name = "category_name")
    private String categoryName;
    @NotNull
    @Column(name = "category_type")
    //TODO moze enumy?
    private String categoryType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Category(){

    }

    public Category(CategoryCreateInput categoryCreateInput){
        this.categoryName = categoryCreateInput.getCategoryName();
        this.categoryType = categoryCreateInput.getCategoryType();
    }
}
