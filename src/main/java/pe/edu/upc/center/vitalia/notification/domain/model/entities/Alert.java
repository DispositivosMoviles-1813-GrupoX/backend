package pe.edu.upc.center.vitalia.notification.domain.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alerts") // Cambia el nombre si tu tabla es diferente
public class Alert {
    @Id
    private Long id;
    private String severity;
    private String triggeredBy;

    public Alert(String severity, String triggeredBy) {
        this.severity = severity;
        this.triggeredBy = triggeredBy;
    }

  public Alert() {

  }

  public void flagAsCritical() {
        this.severity = "Critical";
    }

    public void sendToEmergencyContact() {
        // Logic to send alert to emergency contact
    }

    // Getters and setters omitted for brevity

    // Revisa todos los atributos de la clase Alert.
    // Marca con @Transient cualquier atributo que sea de tipo personalizado o no básico de JPA.
    // Ejemplo:
    // @Transient
    // private Notification notification;
    // @Transient
    // private OtroTipoNoSoportado otroCampo;
}