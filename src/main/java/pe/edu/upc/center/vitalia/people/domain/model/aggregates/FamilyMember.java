package pe.edu.upc.center.vitalia.people.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.people.domain.model.valueobjects.FullName;

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

    // Getter methods
    public Long getId() {
        return id;
    }

    public String getRelationship() {
        return relationship;
    }

    public Long getLinkedResidentId() {
        return linkedResidentId;
    }

    public FullName getFullName() {
        return fullName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    // Setter methods
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setLinkedResidentId(Long linkedResidentId) {
        this.linkedResidentId = linkedResidentId;
    }
}
