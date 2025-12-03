package pe.edu.upc.center.vitalia.users.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.vitalia.users.domain.model.aggregates.FamilyMember;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.UserId;

import java.util.Optional;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
  public boolean existsByUserId(UserId userId);
  Optional<FamilyMember> findByLinkedResidentId(Long linkedResidentId);
}
