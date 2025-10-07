package pe.edu.upc.center.vitalia.iam.domain.services;

import pe.edu.upc.center.vitalia.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
  void handle(SeedRolesCommand command);
}
