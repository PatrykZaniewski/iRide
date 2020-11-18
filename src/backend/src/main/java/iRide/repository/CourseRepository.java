package iRide.repository;

import iRide.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("SELECT course FROM Course course WHERE CAST(course.instructor.id AS string) LIKE ?1 AND CAST(course.student.id AS string) LIKE ?2 AND CAST(course.category.id AS string) LIKE ?3 AND course.status IN ?4")
    Optional<List<Course>> getCoursesByParameters(String instructorId, String studentId, String categoryId, List<String> status);

}
