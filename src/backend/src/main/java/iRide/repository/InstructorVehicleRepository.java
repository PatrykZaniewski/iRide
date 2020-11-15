package iRide.repository;

import iRide.model.InstructorVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorVehicleRepository extends JpaRepository<InstructorVehicle, Integer> {

    @Query("SELECT instructorVehicle.instructor.id FROM InstructorVehicle instructorVehicle WHERE instructorVehicle.vehicle.id = ?1")
    Optional<List<Integer>> getIdsOfVehicleInstructors(int vehicleId);

    @Modifying
    @Query("DELETE FROM InstructorVehicle instructorVehicle WHERE instructorVehicle.vehicle.id = ?1")
    void deleteVehicleInstructors(int vehicleId);

}
