package pe.edu.upc.center.vitalia.users.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.vitalia.iam.domain.model.aggregates.User;
import pe.edu.upc.center.vitalia.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.UserId;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
  //public boolean existsByUserId(Long userId);
  //UserId user(User user);
  Optional<Doctor> findByUserId(Long userId);

  // Verifica si existe usando el ID numérico
  boolean existsByUserId(Long userId);
}
