package iRide.repository;

import iRide.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT category.id FROM Category WHERE category.category_name == ?1 AND category.category_type == ?2")
    Optional<Integer> getCategoryIdByNameByType(String categoryName, String categoryType);


}
