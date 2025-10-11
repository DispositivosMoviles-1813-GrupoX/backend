package pe.edu.upc.center.vitalia.people.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.FullName;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationNumber;
    private String assignedWard;

    @Embedded
    private FullName fullName;

    @Embedded
    private ContactInfo contactInfo;
}
