package iRide.repository;

import iRide.model.InstructorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorCategoryRepository extends JpaRepository<InstructorCategory, Long> {
}
