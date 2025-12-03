package pe.edu.upc.center.vitalia.residents.application.internal.commandservices;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.residents.application.internal.outboundservices.ExternalIamService;
import pe.edu.upc.center.vitalia.residents.application.internal.outboundservices.ExternalUsersService;
import pe.edu.upc.center.vitalia.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.vitalia.residents.domain.model.commands.CreateResidentCommand;
import pe.edu.upc.center.vitalia.residents.domain.model.commands.DeleteResidentCommand;
import pe.edu.upc.center.vitalia.residents.domain.model.commands.UpdateResidentCommand;
import pe.edu.upc.center.vitalia.residents.domain.services.ResidentCommandService;
import pe.edu.upc.center.vitalia.residents.infrastructure.persistence.jpa.repositories.ResidentRepository;
import pe.edu.upc.center.vitalia.shared.domain.events.ResidentCreatedEvent;

import java.util.Optional;

@Service
public class ResidentCommandServiceImpl implements ResidentCommandService {

    private final ResidentRepository residentRepository;
    private final ExternalUsersService externalUsersService;
    private final ExternalIamService externalIamService;
    private final ApplicationEventPublisher publisher;

    public ResidentCommandServiceImpl(ResidentRepository residentRepository,
                                      ExternalUsersService externalUsersService,
                                      ExternalIamService externalIamService,
                                      ApplicationEventPublisher publisher) {
        this.residentRepository = residentRepository;
        this.externalUsersService = externalUsersService;
        this.externalIamService = externalIamService;
        this.publisher = publisher;
    }

    @Override
    public Long handle(CreateResidentCommand command) {
        if (this.residentRepository.existsByDni(command.dni())) {
            throw new IllegalArgumentException("Resident with DNI " + command.dni() + " already exists");
        }

        Resident resident = new Resident(command);

        try {
            this.residentRepository.save(resident);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving resident: " + e.getMessage());
        }

        var residentId = resident.getId();
        var familyMemberId = externalUsersService.fetchFamilyMemberIdByResidentId(residentId);
        var familyMemberName = externalUsersService.fetchFamilyMemberNameByResidentId(residentId);
        var familyMemberUserId = externalUsersService.fetchFamilyMemberUserIdByResidenId(residentId);
        var familyMemberEmail = externalIamService.fetchEmailByUserId(familyMemberUserId);

        var event = new ResidentCreatedEvent(
            resident.getId(),
            resident.getDni(),
            resident.getFullName().firstName(),
            resident.getFullName().lastName(),
            resident.getGender(),
            resident.getAddress().city(),
            resident.getAddress().country(),
            familyMemberId,
            familyMemberName,
            familyMemberUserId,
            familyMemberEmail);
        publisher.publishEvent(event);


        return resident.getId();
    }

    @Override
    public Optional<Resident> handle(UpdateResidentCommand command) {
        var residentId = command.residentId();

        if (!this.residentRepository.existsById(residentId)) {
            throw new IllegalArgumentException("Resident with id " + residentId + " does not exist");
        }

        if (this.residentRepository.existsByDniAndIdIsNot(command.dni(), residentId)) {
            throw new IllegalArgumentException("Resident with DNI " + command.dni() + " already exists");
        }

        var residentToUpdate = this.residentRepository.findById(residentId).get();
        residentToUpdate.updateInformation(
                command.dni(),
                command.fullName(),
                command.address(),
                command.birthDate(),
                command.gender(),
                command.receipt()
        );

        try {
            var updatedResident = this.residentRepository.save(residentToUpdate);
            return Optional.of(updatedResident);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating resident: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteResidentCommand command) {
        if (!this.residentRepository.existsById(command.residentId())) {
            throw new IllegalArgumentException("Resident with id " + command.residentId() + " does not exist");
        }

        try {
            this.residentRepository.deleteById(command.residentId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting resident: " + e.getMessage());
        }
    }
}