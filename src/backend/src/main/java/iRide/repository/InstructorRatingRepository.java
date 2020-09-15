package iRide.repository;

import iRide.model.InstructorRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRatingRepository extends JpaRepository<InstructorRating, Integer> {
}
