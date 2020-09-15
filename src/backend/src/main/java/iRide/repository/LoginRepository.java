package iRide.repository;

import iRide.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer>{

    @Query("SELECT login FROM Login WHERE login.email == ?1")
    Optional<Login> getLoginByEmail(String email);
}
