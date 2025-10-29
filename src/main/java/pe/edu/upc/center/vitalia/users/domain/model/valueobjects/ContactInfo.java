package pe.edu.upc.center.vitalia.users.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ContactInfo {
    private String phone;

    @Embedded
    private Address address;
}
