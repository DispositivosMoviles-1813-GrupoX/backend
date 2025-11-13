package pe.edu.upc.center.vitalia.users.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.FullName;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "family_members")
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String relationship;
    private Long linkedResidentId;

    @Embedded
    private FullName fullName;

    @Embedded
    private ContactInfo contactInfo;
}
