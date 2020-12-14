package iRide.repository;

import iRide.model.Instructor;
import iRide.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    @Query("SELECT instructor FROM Instructor instructor WHERE instructor.user.id = ?1")
    Optional<Instructor> getInstructorByUserId(int userId);
}
