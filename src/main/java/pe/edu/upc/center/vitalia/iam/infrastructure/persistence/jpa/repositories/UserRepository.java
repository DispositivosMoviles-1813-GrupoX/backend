package pe.edu.upc.center.vitalia.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.vitalia.iam.domain.model.aggregates.User;
import pe.edu.upc.center.vitalia.iam.domain.model.valueobjects.EmailAddress;

import java.util.Optional;

/**
 * This interface is responsible for providing the User entity related operations.
 * It extends the JpaRepository interface.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
  /**
   * This method is responsible for finding the user by username.
   * @param username The username.
   * @return The user object.
   */
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(EmailAddress email);

  /**
   * This method is responsible for checking if the user exists by username.
   * @param username The username.
   * @return True if the user exists, false otherwise.
   */
  boolean existsByUsername(String username);

  boolean existsByEmail(EmailAddress email);
}
