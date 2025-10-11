package pe.edu.upc.center.vitalia.people.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.FullName;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "people")
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String role;

    private String gender;
    private String dni;

    @Embedded
    private FullName fullName;

    @Temporal(TemporalType.DATE)
    private Date birthDate;


    private String name;



    @Embedded
    private ContactInfo contactInfo;

    // Puedes agregar métodos como updateContactInfo, getAge, etc.
}
