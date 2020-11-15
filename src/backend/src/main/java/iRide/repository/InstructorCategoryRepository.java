package iRide.repository;

import iRide.model.InstructorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorCategoryRepository extends JpaRepository<InstructorCategory, Integer> {

    @Query("SELECT instructorCategory.category.id FROM InstructorCategory instructorCategory WHERE instructorCategory.instructor.id = ?1")
    Optional<List<Integer>> getIdsOfInstructorCategories(int instructorId);

    @Modifying
    @Query("DELETE FROM InstructorCategory instructorCategory WHERE instructorCategory.instructor.id = ?1")
    void deleteInstructorCategories(int instructorId);

}
