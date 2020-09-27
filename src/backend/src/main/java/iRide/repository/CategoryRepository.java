package iRide.repository;

import iRide.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT category FROM Category category WHERE category.categoryName = ?1 AND category.categoryType = ?2")
    Optional<Category> getCategoryByNameByType(String categoryName, String categoryType);


}
