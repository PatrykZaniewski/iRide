package iRide.repository;

import iRide.model.InstructorVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorVehicleRepository extends JpaRepository<InstructorVehicle, Integer> {
}
