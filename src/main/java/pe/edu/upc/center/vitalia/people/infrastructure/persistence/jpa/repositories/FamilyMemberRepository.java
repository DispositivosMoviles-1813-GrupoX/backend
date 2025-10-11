package pe.edu.upc.center.vitalia.people.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.vitalia.people.domain.model.aggregates.FamilyMember;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
}
