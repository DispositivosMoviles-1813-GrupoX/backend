package pe.edu.upc.center.vitalia.users.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class FullName {
    private String firstName;
    private String lastName;
}
