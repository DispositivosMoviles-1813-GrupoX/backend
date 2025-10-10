package pe.edu.upc.center.vitalia.residents.domain.services;

import pe.edu.upc.center.vitalia.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.vitalia.residents.domain.model.commands.CreateResidentCommand;
import pe.edu.upc.center.vitalia.residents.domain.model.commands.DeleteResidentCommand;
import pe.edu.upc.center.vitalia.residents.domain.model.commands.UpdateResidentCommand;

import java.util.Optional;

public interface ResidentCommandService {
    Long handle(CreateResidentCommand command);
    Optional<Resident> handle(UpdateResidentCommand command);
    void handle(DeleteResidentCommand command);
}
