package iRide.repository;

import iRide.model.Instructor;
import iRide.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT student FROM Student student WHERE student.user.id = ?1")
    Optional<Student> getStudentByUserId(int userId);
}
