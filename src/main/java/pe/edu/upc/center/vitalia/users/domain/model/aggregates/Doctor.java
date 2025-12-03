package pe.edu.upc.center.vitalia.users.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.center.vitalia.iam.domain.model.aggregates.User;
import org.springframework.lang.Nullable;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.FullName;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.Schedule;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.UserId;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
    @AttributeOverride(name = "userId.value", column = @Column(name = "user_id"))
})
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private String specialty;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "doctor_id")
    private List<Schedule> schedules = new ArrayList<>();

    @Embedded
    private FullName fullName;

    @Embedded
    private ContactInfo contactInfo;

    //@Nullable
    //@Embedded
    //private UserId userId;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
