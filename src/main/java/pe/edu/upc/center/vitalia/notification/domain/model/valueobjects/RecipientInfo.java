package pe.edu.upc.center.vitalia.notification.domain.model.valueobjects;

/**
 * Value object representing recipient information.
 */
public class RecipientInfo {
    private String name;
    private String email;

    public RecipientInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "RecipientInfo{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}