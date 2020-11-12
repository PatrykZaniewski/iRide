package iRide.repository;

import iRide.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    @Query("SELECT vehicle FROM Vehicle vehicle WHERE vehicle.mark = ?1 AND vehicle.model = ?2 AND vehicle.plateNumber = ?3 AND vehicle.vin = ?4")
    Optional<Vehicle> getVehicleByParameters(String mark, String model, String plateNumber, String vin);
}
