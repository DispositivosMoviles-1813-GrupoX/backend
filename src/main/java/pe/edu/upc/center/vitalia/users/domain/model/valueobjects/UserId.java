package pe.edu.upc.center.vitalia.users.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record UserId(@NotNull Long value) {
  public UserId {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("Leader ID cannot be null or negative.");
    }
  }
}
