package pe.edu.upc.center.vitalia.payment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record FinancialInfo(String paymentMethod) {
    public FinancialInfo() {
        this(null);
    }
    public FinancialInfo {
        if (paymentMethod == null || paymentMethod.isBlank()) {
            throw new IllegalArgumentException("Payment Method cannot be null or blank");
        }
    }
}
