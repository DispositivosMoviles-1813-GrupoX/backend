package pe.edu.upc.center.vitalia.users.application.internal.eventhandlers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pe.edu.upc.center.vitalia.iam.domain.model.aggregates.User;
import pe.edu.upc.center.vitalia.shared.domain.events.UserCreatedEvent;
import pe.edu.upc.center.vitalia.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.vitalia.users.domain.model.valueobjects.FullName;
import pe.edu.upc.center.vitalia.users.domain.services.DoctorCommandService;
import pe.edu.upc.center.vitalia.users.infrastructure.persistence.jpa.repositories.DoctorRepository;

import java.util.ArrayList;

@Component
public class UserCreatedEventHandler {

    private final DoctorCommandService doctorCommandService;
    private final DoctorRepository doctorRepository;

    public UserCreatedEventHandler(DoctorCommandService doctorCommandService, DoctorRepository doctorRepository) {
        this.doctorCommandService = doctorCommandService;
        this.doctorRepository = doctorRepository;
    }

    @EventListener
    public void on(UserCreatedEvent event) {
        // CORRECCIÓN 1: Usamos .userId() (formato Record)
        if (doctorRepository.existsByUserId(event.userId())) {
            return;
        }

        System.out.println("⚡ Evento Recibido: Creando perfil de Doctor para Usuario ID: " + event.userId());

        Doctor newDoctor = new Doctor();
        newDoctor.setLicenseNumber("TEMP-" + System.currentTimeMillis());
        newDoctor.setSpecialty("Por definir");

        // CORRECCIÓN 2: Usamos .username() en vez de .getUsername()
        newDoctor.setFullName(new FullName(event.username(), ""));

        newDoctor.setContactInfo(new ContactInfo());
        newDoctor.setSchedules(new ArrayList<>());

        // Asignación del User ID usando reflexión
        User user = new User();
        try {
            var idField = user.getClass().getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(user, event.userId()); // CORRECCIÓN 3: .userId()
        } catch (Exception e) {
            throw new RuntimeException("Error al vincular usuario en EventHandler", e);
        }
        newDoctor.setUser(user);

        try {
            doctorCommandService.createDoctor(newDoctor);
        } catch (Exception e) {
            System.err.println("Error creando doctor automático: " + e.getMessage());
        }
    }
}