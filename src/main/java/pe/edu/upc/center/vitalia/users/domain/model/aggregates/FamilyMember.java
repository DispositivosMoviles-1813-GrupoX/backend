package pe.edu.upc.center.vitalia.users.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.FullName;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.UserId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "family_members")
@AttributeOverrides({
    @AttributeOverride(name = "userId.value", column = @Column(name = "user_id"))
})
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

  @Nullable
  @Embedded
  private UserId userId;
}
